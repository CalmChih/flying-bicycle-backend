package top.fallingintodreams.flying.bicycle.backend.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;
import top.fallingintodreams.flying.bicycle.backend.dao.IOrderDao;
import top.fallingintodreams.flying.bicycle.backend.po.Order;
import top.fallingintodreams.flying.bicycle.backend.service.OrderService;

import java.util.List;

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

    @Override
    public void insertOrder(Order order) {
        orderDao.insertOrder(order);
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
