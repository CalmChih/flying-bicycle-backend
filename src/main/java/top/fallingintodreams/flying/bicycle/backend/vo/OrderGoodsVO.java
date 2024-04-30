package top.fallingintodreams.flying.bicycle.backend.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderGoodsVO {

    private String orderId;
    private String username;
    private Date reservationStartTime;
    private Date reservationEndTime;
    private Integer reservationDays;
    private BigDecimal totalPrice;
    private Date payTime;
    private Integer orderState;
    private Date createTime;
    private Long goodsId;
    private String goodsTitle;
    private String goodsImg;
    private String goodsDesc;
    private BigDecimal goodsPrice;

}
