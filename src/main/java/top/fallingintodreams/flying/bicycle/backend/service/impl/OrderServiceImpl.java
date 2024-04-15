package top.fallingintodreams.flying.bicycle.backend.service.impl;

import jakarta.annotation.Resource;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Repository;
import top.fallingintodreams.flying.bicycle.backend.common.ApiResponse;
import top.fallingintodreams.flying.bicycle.backend.common.Constant;
import top.fallingintodreams.flying.bicycle.backend.dao.IOrderDao;
import top.fallingintodreams.flying.bicycle.backend.dto.OrderDTO;
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
        return ApiResponse.success();
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
    public Order getOrderInfo(Order order) {
        return orderDao.getOrderInfo(order);
    }

}
