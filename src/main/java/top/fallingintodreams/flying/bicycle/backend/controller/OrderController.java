package top.fallingintodreams.flying.bicycle.backend.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import top.fallingintodreams.flying.bicycle.backend.common.ApiResponse;
import top.fallingintodreams.flying.bicycle.backend.dto.OrderDTO;
import top.fallingintodreams.flying.bicycle.backend.po.Order;
import top.fallingintodreams.flying.bicycle.backend.service.OrderService;

import java.util.List;

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

    @GetMapping("/getOrderList")
    public ApiResponse getOrderList(@RequestParam Long accountId) {
        List<Order> orderList = orderService.getOrderList(accountId);
        return ApiResponse.success(orderList);
    }

    @GetMapping("/getOrderInfo")
    public ApiResponse getOrderInfo(@RequestBody Order order) {
        Order orderInfo = orderService.getOrderInfo(order);
        return ApiResponse.success(orderInfo);
    }

}
