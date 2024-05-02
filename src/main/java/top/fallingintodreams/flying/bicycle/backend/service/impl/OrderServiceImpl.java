package top.fallingintodreams.flying.bicycle.backend.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Repository;
import top.fallingintodreams.flying.bicycle.backend.common.ApiResponse;
import top.fallingintodreams.flying.bicycle.backend.common.Constant;
import top.fallingintodreams.flying.bicycle.backend.config.MyAlipayConfig;
import top.fallingintodreams.flying.bicycle.backend.dao.IOrderDao;
import top.fallingintodreams.flying.bicycle.backend.dto.OrderDTO;
import top.fallingintodreams.flying.bicycle.backend.dto.OrderGoodsDTO;
import top.fallingintodreams.flying.bicycle.backend.po.Goods;
import top.fallingintodreams.flying.bicycle.backend.po.Order;
import top.fallingintodreams.flying.bicycle.backend.service.GoodsService;
import top.fallingintodreams.flying.bicycle.backend.service.OrderService;
import top.fallingintodreams.flying.bicycle.backend.util.DateConvertUtils;
import top.fallingintodreams.flying.bicycle.backend.util.IdUtil;
import top.fallingintodreams.flying.bicycle.backend.util.RedisUtil;
import top.fallingintodreams.flying.bicycle.backend.vo.OrderGoodsVO;
import top.fallingintodreams.flying.bicycle.backend.vo.OrderStateCountVO;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    private GoodsService goodsService;

    @Resource
    private RedisUtil redisUtil;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private MyAlipayConfig myAlipayConfig;
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
    public ApiResponse cancelOrder(Map<String, Object> searchMap) {
        Date reservationStartTime = DateConvertUtils.formatToDate(searchMap.get("reservationStartTime").toString());
        Date nowDate = DateUtils.addSeconds(new Date(), -1);
        String orderState = searchMap.get("orderState").toString();
        if (!Constant.OrderStateEnum.UNPAID.getStateValue().equals(orderState) && nowDate.after(reservationStartTime)) {
            return ApiResponse.error("取消订单失败，当前时间已超过预约的租赁时间");
        }
        Order order = new Order();
        order.setOrderState(Constant.OrderStateEnum.CANCELLED.getStateCode());
        order.setOrderId(searchMap.get("orderId").toString());
        orderDao.updateOrder(order);
        return ApiResponse.success();
    }

    @Override
    public ApiResponse pickupBicycle(Map<String, Object> searchMap) {
        Date reservationStartTime = DateConvertUtils.formatToDate(searchMap.get("reservationStartTime").toString());
        Date canPickupTime = DateUtils.addMinutes(reservationStartTime, -10);
        Date nowDate = new Date();
        if (nowDate.before(canPickupTime)) {
            return ApiResponse.error("最多仅可提前10分钟取车");
        }
        Date reservationEndTime = DateConvertUtils.formatToDate(searchMap.get("reservationEndTime").toString());
        if (nowDate.after(reservationEndTime)) {
            Order order = new Order();
            order.setOrderState(Constant.OrderStateEnum.VEHICLES_RETURNED.getStateCode());
            order.setOrderId(searchMap.get("orderId").toString());
            orderDao.updateOrder(order);
            return ApiResponse.error("当前时间已超过还车时间，交易完成");
        }
        Order order = new Order();
        order.setOrderState(Constant.OrderStateEnum.PICKED_UP.getStateCode());
        order.setOrderId(searchMap.get("orderId").toString());
        orderDao.updateOrder(order);
        return ApiResponse.success();
    }

    @Override
    public ApiResponse returnBicycle(Map<String, Object> searchMap) {
        Order order = new Order();
        order.setOrderId(searchMap.get("orderId").toString());
        order.setOrderState(Constant.OrderStateEnum.VEHICLES_RETURNED.getStateCode());
        orderDao.updateOrder(order);
        return ApiResponse.success();
    }

    @Override
    public Map<String, Object> getOrderList(Map<String, Object> searchMap) {
        if (searchMap.get("currentPage") != null && searchMap.get("pageSize") != null) {
            int currentPage = (int) searchMap.get("currentPage");
            int pageSize = (int) searchMap.get("pageSize");
            PageHelper.startPage(currentPage, pageSize);
        }
        List<OrderGoodsVO> orderList = orderDao.getOrderList(searchMap);
        List<OrderGoodsDTO> orderGoodsDTOList = new ArrayList<>();
        orderList.forEach(orderGoodsVO -> orderGoodsDTOList.add(convert2DTO(orderGoodsVO)));
        PageInfo<OrderGoodsVO> pageInfo = new PageInfo<>(orderList);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("list", orderGoodsDTOList);
        resultMap.put("total", pageInfo.getTotal());
        return resultMap;
    }

    @Override
    public OrderGoodsDTO getOrderInfo(String orderId) {
        OrderGoodsVO orderGoodsVO = orderDao.getOrderInfo(orderId);
        return convert2DTO(orderGoodsVO);
    }

    private OrderGoodsDTO convert2DTO(OrderGoodsVO orderGoodsVO) {
        OrderGoodsDTO orderGoodsDTO = new OrderGoodsDTO();
        if (Objects.isNull(orderGoodsVO)) {
            return null;
        }
        orderGoodsDTO.setOrderId(orderGoodsVO.getOrderId());
        orderGoodsDTO.setUsername(orderGoodsVO.getUsername());
        orderGoodsDTO.setReservationStartTime(DateConvertUtils.dateFormatToString(orderGoodsVO.getReservationStartTime()));
        orderGoodsDTO.setReservationEndTime(DateConvertUtils.dateFormatToString(orderGoodsVO.getReservationEndTime()));
        orderGoodsDTO.setReservationDays(orderGoodsVO.getReservationDays());
        orderGoodsDTO.setTotalPrice(orderGoodsVO.getTotalPrice());
        orderGoodsDTO.setPayTime(DateConvertUtils.dateFormatToString(orderGoodsVO.getPayTime()));
        orderGoodsDTO.setOrderState(Constant.OrderStateEnum.convert(orderGoodsVO.getOrderState()));
        orderGoodsDTO.setCreateTime(DateConvertUtils.dateFormatToString(orderGoodsVO.getCreateTime()));
        orderGoodsDTO.setGoodsId(orderGoodsVO.getGoodsId());
        orderGoodsDTO.setGoodsTitle(orderGoodsVO.getGoodsTitle());
        orderGoodsDTO.setGoodsImg(orderGoodsVO.getGoodsImg());
        orderGoodsDTO.setGoodsDesc(orderGoodsVO.getGoodsDesc());
        orderGoodsDTO.setGoodsPrice(orderGoodsVO.getGoodsPrice());
        return orderGoodsDTO;
    }

    @Override
    public List<OrderStateCountVO> getOrderCountGroupByState(String username) {
        return orderDao.getOrderCountGroupByState(username);
    }

    @Override
    public String payOrder(String orderId) {
        OrderGoodsVO orderGoodsVO = orderDao.getOrderInfo(orderId);
        AlipayClient alipayClient;
        try {
            alipayClient = new DefaultAlipayClient(alipayConfig);
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }

        AlipayTradeWapPayRequest request = getAlipayTradeWapPayRequest(orderGoodsVO);
        AlipayTradeWapPayResponse response;
        try {
            response = alipayClient.pageExecute(request, "POST");
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
        return response.getBody();
    }

    @NotNull
    private AlipayTradeWapPayRequest getAlipayTradeWapPayRequest(OrderGoodsVO orderGoodsVO) {
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        request.setNotifyUrl(myAlipayConfig.getNotifyUrl());
        request.setReturnUrl("http://localhost:8026/user");
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        model.setOutTradeNo(orderGoodsVO.getOrderId());
        model.setTotalAmount(orderGoodsVO.getTotalPrice().toString());
        model.setSubject(orderGoodsVO.getGoodsTitle());
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
        boolean checkContent;
        try {
            checkContent = AlipaySignature.rsa256CheckContent(signatureContent, sign, alipayConfig.getAlipayPublicKey(), alipayConfig.getCharset());
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
        if (checkContent) {
            String tradeStatus = params.get("trade_status");
            if ("TRADE_SUCCESS".equals(tradeStatus)) {
                String outTradeNo = params.get("out_trade_no");
                String gmtPayment = params.get("gmt_payment");
                Order order = new Order();
                order.setOrderId(outTradeNo);
                order.setOrderState(Constant.OrderStateEnum.PAID.getStateCode());
                try {
                    order.setPayTime(DateUtils.parseDate(gmtPayment, "yyyy-MM-dd HH:mm:ss"));
                } catch (ParseException e) {
                    log.error("时间转换异常，orderId：{}，payTime：{}", outTradeNo, gmtPayment);
                }
                orderDao.updateOrder(order);
            }
        }
    }

}
