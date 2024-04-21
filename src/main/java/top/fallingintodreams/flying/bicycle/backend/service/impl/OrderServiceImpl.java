package top.fallingintodreams.flying.bicycle.backend.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Repository;
import top.fallingintodreams.flying.bicycle.backend.common.ApiResponse;
import top.fallingintodreams.flying.bicycle.backend.common.Constant;
import top.fallingintodreams.flying.bicycle.backend.config.AlipayConfig;
import top.fallingintodreams.flying.bicycle.backend.dao.IGoodsDao;
import top.fallingintodreams.flying.bicycle.backend.dao.IOrderDao;
import top.fallingintodreams.flying.bicycle.backend.dto.OrderDTO;
import top.fallingintodreams.flying.bicycle.backend.dto.OrderInfoDTO;
import top.fallingintodreams.flying.bicycle.backend.po.Goods;
import top.fallingintodreams.flying.bicycle.backend.po.Order;
import top.fallingintodreams.flying.bicycle.backend.service.GoodsService;
import top.fallingintodreams.flying.bicycle.backend.service.OrderService;
import top.fallingintodreams.flying.bicycle.backend.util.IdUtil;
import top.fallingintodreams.flying.bicycle.backend.util.RedisUtil;

import java.text.ParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单 服务实现类
 *
 * @author Chih
 * @date 2024/2/29 23:03
 */
@Slf4j
@Repository
public class OrderServiceImpl implements OrderService {

    @Resource
    private IOrderDao orderDao;
    @Resource
    private IGoodsDao goodsDao;

    @Resource
    private GoodsService goodsService;

    @Resource
    private RedisUtil redisUtil;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private AlipayConfig alipayConfig;

    private static final DefaultRedisScript<Long> STOCKS_DECREMENT;

    static {
        STOCKS_DECREMENT = new DefaultRedisScript<>();
        STOCKS_DECREMENT.setLocation(new ClassPathResource("/lua/stocks_decrement.lua"));
        STOCKS_DECREMENT.setResultType(Long.class);
    }

    @Override
    public ApiResponse insertOrder(OrderDTO orderDTO) {
        String hMapKey = Constant.RedisKey.VALID_GOODS_LIST(orderDTO.getReservationStartTime(),
                orderDTO.getReservationEndTime());
        if (redisUtil.hasKey(hMapKey)) {
            Long res = redisTemplate.execute(STOCKS_DECREMENT, Collections.singletonList(hMapKey),
                    orderDTO.getGoodsId());
            if (res.equals(0L)) {
                return ApiResponse.error("车辆库存不足，请修改预约时间后再试");
            }
        } else {
            Map<String, Object> searchMap = new HashMap<>();
            searchMap.put("reservationStartTime", orderDTO.getReservationStartTime());
            searchMap.put("reservationEndTime", orderDTO.getReservationEndTime());
            List<Goods> validGoodsList = goodsService.getValidGoodsList(searchMap);
            long count = validGoodsList.stream().filter(goods -> goods.getGoodsId().equals(orderDTO.getGoodsId())).count();
            if (count == 0) {
                return ApiResponse.error("车辆库存不足，请修改预约时间后再试");
            }
            long decr = redisUtil.hdecr(hMapKey, orderDTO.getGoodsId().toString(), 1);
            if (decr < 0) {
                redisUtil.hdel(hMapKey, orderDTO.getGoodsId().toString());
                return ApiResponse.error("车辆库存不足，请修改预约时间后再试");
            }
        }
        Order order = new Order();
        order.setOrderId(IdUtil.generateOrderId());
        order.setUsername(orderDTO.getUsername());
        order.setGoodsId(orderDTO.getGoodsId());
        try {
            order.setReservationStartTime(DateUtils.parseDate(orderDTO.getReservationStartTime(), "yyyy-MM-dd HH:mm"));
            order.setReservationEndTime(DateUtils.parseDate(orderDTO.getReservationEndTime(), "yyyy-MM-dd HH:mm"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        order.setReservationDays(orderDTO.getReservationDays());
        order.setTotalPrice(orderDTO.getTotalPrice());
        orderDao.insertOrder(order);
        return ApiResponse.success(order.getOrderId(), "下单成功");
    }

    @Override
    public void updateOrder(Order order) {
        orderDao.updateOrder(order);
    }

    @Override
    public List<Order> getOrderList(Long accountId) {
        return orderDao.getOrderList(accountId);
    }

    @Override
    public OrderInfoDTO getOrderInfo(String orderId) {
        Order order = orderDao.getOrderInfo(orderId);
        Goods goods = goodsDao.getGoodsById(order.getGoodsId());
        OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
        orderInfoDTO.setUsername(order.getUsername());
        orderInfoDTO.setOrderId(order.getOrderId());
        orderInfoDTO.setReservationStartTime(DateFormatUtils.format(order.getReservationStartTime(), "yyyy-MM-dd HH:mm"));
        orderInfoDTO.setReservationEndTime(DateFormatUtils.format(order.getReservationEndTime(), "yyyy-MM-dd HH:mm"));
        orderInfoDTO.setCreateTime(DateFormatUtils.format(order.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
        orderInfoDTO.setReservationDays(order.getReservationDays());
        orderInfoDTO.setTotalPrice(order.getTotalPrice());
        orderInfoDTO.setGoodsTitle(goods.getGoodsTitle());
        orderInfoDTO.setGoodsDesc(goods.getGoodsDesc());
        orderInfoDTO.setGoodsImg(goods.getGoodsImg());
        return orderInfoDTO;
    }

    @Override
    public String payOrder(String orderId) {
        Order orderInfo = orderDao.getOrderInfo(orderId);
        Goods goods = goodsDao.getGoodsById(orderInfo.getGoodsId());
        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig.getServerUrl(),
                alipayConfig.getAppId(), alipayConfig.getPrivateKey(), alipayConfig.getFormat(),
                alipayConfig.getCharset(), alipayConfig.getAlipayPublicKey(), alipayConfig.getSignType());
        log.info("配置：{}", JSON.toJSONString(alipayConfig));

        AlipayTradeWapPayRequest request = getAlipayTradeWapPayRequest(orderInfo, goods);
        AlipayTradeWapPayResponse response;
        try {
            response = alipayClient.pageExecute(request, "POST");
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
        log.info(response.getBody());
        return response.getBody();
    }

    @NotNull
    private AlipayTradeWapPayRequest getAlipayTradeWapPayRequest(Order orderInfo, Goods goods) {
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        request.setNotifyUrl(alipayConfig.getNotifyUrl());
        request.setReturnUrl("http://localhost:5173/user");
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        model.setOutTradeNo(orderInfo.getOrderId());
        model.setTotalAmount(orderInfo.getTotalPrice().toString());
        model.setSubject(goods.getGoodsTitle());
        model.setProductCode("QUICK_WAP_WAY");
        model.setSellerId("2088721033871118");
        request.setBizModel(model);
        return request;
    }

    @Override
    public void payCallback(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, String> params = new HashMap<>();
        for (String key : parameterMap.keySet()) {
            params.put(key, request.getParameter(key));
        }
        String sign = params.get("sign");
        String signatureContent = AlipaySignature.getSignCheckContentV1(params);
        params.forEach((key, value) -> {
            log.info(key + ":" + value);
        });
        boolean checkContent = false;
        try {
            checkContent = AlipaySignature.rsa256CheckContent(signatureContent, sign, alipayConfig.getAlipayPublicKey(), alipayConfig.getCharset());
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
        if (checkContent) {
            params.forEach((key, value) -> {
                log.info(key + ":" + value);
            });
        }
    }
}
