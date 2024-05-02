package top.fallingintodreams.flying.bicycle.backend.dao;

import org.apache.ibatis.annotations.Mapper;
import top.fallingintodreams.flying.bicycle.backend.po.Order;
import top.fallingintodreams.flying.bicycle.backend.vo.GoodsRentalRankingVO;
import top.fallingintodreams.flying.bicycle.backend.vo.OrderGoodsVO;
import top.fallingintodreams.flying.bicycle.backend.vo.OrderStateCountVO;
import top.fallingintodreams.flying.bicycle.backend.vo.OrderTrendVO;
import top.fallingintodreams.flying.bicycle.backend.vo.ReservationGoodsVO;

import java.util.List;
import java.util.Map;

/**
 * 订单Dao
 *
 * @author Chih
 * @date 2024/2/28 23:28
 */
@Mapper
public interface IOrderDao {

    /**
     * 新增订单
     * @param order 订单信息
     */
    void insertOrder(Order order);

    /**
     * 更新订单状态 和 支付时间
     * @param order 订单信息
     */
    void updateOrder(Order order);

    /**
     * 获取订单列表 分页、
     * @return 订单列表
     */
    List<OrderGoodsVO> getOrderList(Map<String, Object> searchMap);

    /**
     * 获取订单信息
     * @param orderId 订单id
     * @return 订单信息
     */
    OrderGoodsVO getOrderInfo(String orderId);

    List<ReservationGoodsVO> getReservationGoodsNum(Map<String, Object> searchMap);

    List<OrderStateCountVO> getOrderCountGroupByState(String username);

    List<GoodsRentalRankingVO> getGoodsRentalRanking();

    List<OrderTrendVO> getOrderTrend(Map<String, Object> searchMap);

}
