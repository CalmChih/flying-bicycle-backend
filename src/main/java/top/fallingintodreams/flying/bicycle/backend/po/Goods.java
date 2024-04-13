package top.fallingintodreams.flying.bicycle.backend.po;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品表
 *
 * @author Chih
 * @date 2024/2/28 23:19
 */
@Data
public class Goods {
    /**
     * 自增id
     */
    private Long id;
    /**
     * 商品id
     */
    private Long goodsId;
    /**
     * 商品标题
     */
    private String goodsTitle;
    /**
     * 商品描述
     */
    private String goodsDesc;
    /**
     * 商品数量
     */
    private Integer goodsNum;
    /**
     * 商品金额
     */
    private BigDecimal goodsPrice;
    /**
     * 商品分类id
     */
    private Integer categoryId;
    /**
     * 商品标签
     */
    private String goodsTags;
    /**
     * 商品图片
     */
    private String goodsImg;
    /**
     * 商品状态
     */
    private Integer state;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

}
