package top.fallingintodreams.flying.bicycle.backend.po;

import lombok.Data;

import java.util.Date;

/**
 * 订单表
 *
 * @author Chih
 * @date 2024/2/28 23:19
 */
@Data
public class Order {
    /**
     * 主键id
     */
    private Long id;
    /**
     * 订单id
     */
    private Long orderId;
    /**
     * 账号
     */
    private Long username;
    /**
     * 商品id
     */
    private Long goodsId;
    /**
     * 租赁开始时间
     */
    private Date reservationStartTime;
    /**
     * 租赁结束时间
     */
    private Date reservationEndTime;
    /**
     * 支付时间
     */
    private Date payTime;
    /**
     * 订单状态
     */
    private Integer orderState;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
}
