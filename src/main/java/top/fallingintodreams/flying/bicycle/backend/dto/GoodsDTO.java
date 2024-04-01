package top.fallingintodreams.flying.bicycle.backend.dto;

import lombok.Data;

@Data
public class GoodsDTO {
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
     * 商品分类id
     */
    private Integer categoryId;
    /**
     * 商品分类
     */
    private String categoryName;
    /**
     * 商品标签
     */
    private String goodsTags;
    /**
     * 商品图片
     */
    private String goodsImg;
    /**
     * 商品状态id
     */
    private Integer state;
    /**
     * 商品状态
     */
    private String stateStr;
}
