package top.fallingintodreams.flying.bicycle.backend.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderGoodsDTO {

    private String orderId;
    private String reservationStartTime;
    private String reservationEndTime;
    private Integer reservationDays;
    private BigDecimal totalPrice;
    private String payTime;
    private String orderState;
    private String createTime;
    private String goodsTitle;
    private String goodsImg;
    private String goodsDesc;
    private BigDecimal goodsPrice;

}
