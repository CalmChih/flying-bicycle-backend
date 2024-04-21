package top.fallingintodreams.flying.bicycle.backend.controller;

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
import top.fallingintodreams.flying.bicycle.backend.dto.OrderInfoDTO;
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
    public ApiResponse getOrderInfo(@RequestParam String orderId) {
        OrderInfoDTO orderInfo = orderService.getOrderInfo(orderId);
        return ApiResponse.success(orderInfo);
    }

    @GetMapping("/pay")
    public ApiResponse payOrder(@RequestParam String orderId) {
        String form = orderService.payOrder(orderId);
//        httpServletResponse.setContentType("text/html;charset=UTF-8");
//        try (PrintWriter writer = httpServletResponse.getWriter();){
//            writer.write(form);
//            writer.flush();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        return ApiResponse.success(form, "成功");
    }

    @PostMapping("/payCallback")
    public void payCallback(HttpServletRequest httpServletRequest) {
        orderService.payCallback(httpServletRequest);
    }

}
