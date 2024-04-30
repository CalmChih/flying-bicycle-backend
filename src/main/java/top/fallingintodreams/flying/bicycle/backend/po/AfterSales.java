package top.fallingintodreams.flying.bicycle.backend.po;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class AfterSales {

    private Long id;
    private String orderId;
    private String username;
    private Long goodsId;
    private String goodsTitle;
    private BigDecimal totalPrice;
    private BigDecimal requestRefundAmount;
    private String refundReason;
    private BigDecimal actualRefundAmount;
    private String sellerReply;
    private Integer state;
    private Date createTime;
    private Date updateTime;

}
