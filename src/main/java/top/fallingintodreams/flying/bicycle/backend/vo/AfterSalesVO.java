package top.fallingintodreams.flying.bicycle.backend.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class AfterSalesVO {

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
    private Integer state;
    private Date payTime;
    private Date reservationStartTime;
    private Date reservationEndTime;
    private Date createTime;

}
