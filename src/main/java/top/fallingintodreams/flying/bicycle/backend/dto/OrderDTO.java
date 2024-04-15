package top.fallingintodreams.flying.bicycle.backend.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDTO {

    /**
     * 账号
     */
    private String username;
    /**
     * 商品id
     */
    private Long goodsId;
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

}
