package top.fallingintodreams.flying.bicycle.backend.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderInfoDTO {
    /**
     * 账号
     */
    private String username;
    /**
     * 订单id
     */
    private String orderId;
    /**
     * 租赁开始时间
     */
    private String reservationStartTime;
    /**
     * 租赁结束时间
     */
    private String reservationEndTime;
    /**
     * 租赁天数
     */
    private Integer reservationDays;
    /**
     * 总价
     */
    private BigDecimal totalPrice;
    /**
     * 商品标题
     */
    private String goodsTitle;
    /**
     * 商品描述
     */
    private String goodsDesc;
    /**
     * 商品图片
     */
    private String goodsImg;
    /**
     * 创建时间
     */
    private String createTime;
}
