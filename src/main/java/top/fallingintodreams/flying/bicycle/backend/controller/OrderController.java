package top.fallingintodreams.flying.bicycle.backend.controller;

import cn.dev33.satoken.stp.StpUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.fallingintodreams.flying.bicycle.backend.common.ApiResponse;
import top.fallingintodreams.flying.bicycle.backend.dto.OrderDTO;
import top.fallingintodreams.flying.bicycle.backend.dto.OrderGoodsDTO;
import top.fallingintodreams.flying.bicycle.backend.po.AfterSales;
import top.fallingintodreams.flying.bicycle.backend.po.Order;
import top.fallingintodreams.flying.bicycle.backend.service.OrderService;
import top.fallingintodreams.flying.bicycle.backend.vo.OrderStateCountVO;

import java.util.List;
import java.util.Map;

/**
 * 订单Controller
 *
 * @author Chih
 * @date 2024/2/29 23:06
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    @PostMapping("/addOrder")
    public ApiResponse insertOrder(@RequestBody OrderDTO orderDTO) {
        return orderService.insertOrder(orderDTO);
    }

    @PostMapping("/updateOrder")
    public ApiResponse updateOrder(@RequestBody Order order) {
        orderService.updateOrder(order);
        return ApiResponse.success();
    }

    @PostMapping("/cancelOrder")
    public ApiResponse cancelOrder(@RequestBody Map<String, Object> searchMap) {
        return orderService.cancelOrder(searchMap);
    }

    @PostMapping("/pickupBicycle")
    public ApiResponse pickupBicycle(@RequestBody Map<String, Object> searchMap) {
        return orderService.pickupBicycle(searchMap);
    }

    @PostMapping("/returnBicycle")
    public ApiResponse returnBicycle(@RequestBody Map<String, Object> searchMap) {
        return orderService.returnBicycle(searchMap);
    }

    @PostMapping("/applyForAfterSales")
    public ApiResponse applyForAfterSales(@RequestBody AfterSales afterSales) {
        return orderService.applyForAfterSales(afterSales);
    }

    @PostMapping("/getOrderList")
    public ApiResponse getOrderList(@RequestBody Map<String, Object> searchMap) {
        Map<String, Object> orderList = orderService.getOrderList(searchMap);
        return ApiResponse.success(orderList);
    }

    @GetMapping("/getOrderInfo")
    public ApiResponse getOrderInfo(@RequestParam String orderId) {
        OrderGoodsDTO orderGoodsDTO = orderService.getOrderInfo(orderId);
        return ApiResponse.success(orderGoodsDTO);
    }

    @GetMapping("/getOrderCountGroupByState")
    public ApiResponse getOrderCountGroupByState() {
        String username = StpUtil.getLoginIdAsString();
        List<OrderStateCountVO> orderCountGroupByState = orderService.getOrderCountGroupByState(username);
        return ApiResponse.success(orderCountGroupByState);
    }

    @GetMapping("/pay")
    public ApiResponse payOrder(@RequestParam String orderId) {
        String form = orderService.payOrder(orderId);
        return ApiResponse.success(form, "成功");
    }

    @PostMapping("/payCallback")
    public void payCallback(HttpServletRequest httpServletRequest) {
        orderService.payCallback(httpServletRequest);
    }

}
