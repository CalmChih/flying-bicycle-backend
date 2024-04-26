package top.fallingintodreams.flying.bicycle.backend.service;

import jakarta.servlet.http.HttpServletRequest;
import top.fallingintodreams.flying.bicycle.backend.common.ApiResponse;
import top.fallingintodreams.flying.bicycle.backend.dto.OrderDTO;
import top.fallingintodreams.flying.bicycle.backend.dto.OrderGoodsDTO;
import top.fallingintodreams.flying.bicycle.backend.po.Order;
import top.fallingintodreams.flying.bicycle.backend.vo.OrderStateCountVO;

import java.util.List;
import java.util.Map;

/**
 * 订单 服务接口类
 *
 * @author Chih
 * @date 2024/2/29 22:52
 */
public interface OrderService {
    /**
     * 新增订单
     * @param orderDTO 订单信息
     */
    ApiResponse insertOrder(OrderDTO orderDTO);

    /**
     * 更新订单状态 和 支付时间
     * @param order 订单信息
     */
    void updateOrder(Order order);

    ApiResponse cancelOrder(Map<String, Object> searchMap);

    ApiResponse pickupBicycle(Map<String, Object> searchMap);

    /**
     * 获取订单列表 分页
     * @return 订单列表
     */
    List<OrderGoodsDTO> getOrderList(Map<String, Object> searchMap);

    /**
     * 获取订单信息
     *
     * @param orderId 订单id
     * @return 订单信息
     */
    OrderGoodsDTO getOrderInfo(String orderId);

    List<OrderStateCountVO> getOrderCountGroupByState(String username);

    String payOrder(String orderId);

    void payCallback(HttpServletRequest request);
}
