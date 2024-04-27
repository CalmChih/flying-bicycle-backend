package top.fallingintodreams.flying.bicycle.backend.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderGoodsVO {

    private String orderId;
    private Date reservationStartTime;
    private Date reservationEndTime;
    private Integer reservationDays;
    private BigDecimal totalPrice;
    private Date payTime;
    private Integer orderState;
    private BigDecimal refundAmount;
    private String refundReason;
    private Date createTime;
    private String goodsTitle;
    private String goodsImg;
    private String goodsDesc;
    private BigDecimal goodsPrice;

}
