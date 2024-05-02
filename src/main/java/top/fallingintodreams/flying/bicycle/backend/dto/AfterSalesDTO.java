package top.fallingintodreams.flying.bicycle.backend.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AfterSalesDTO {

    private String orderId;
    private String username;
    private Long goodsId;
    private String goodsTitle;
    private String goodsImg;
    private String goodsDesc;
    private BigDecimal goodsPrice;
    private BigDecimal totalPrice;
    private BigDecimal requestRefundAmount;
    private String refundReason;
    private BigDecimal actualRefundAmount;
    private String sellerReply;
    private String state;
    private String payTime;
    private String reservationStartTime;
    private String reservationEndTime;
    private String createTime;

}
