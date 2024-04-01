package top.fallingintodreams.flying.bicycle.backend.po;

import lombok.Data;

import java.util.Date;
@Data
public class Categorization {

    /**
     * 主键id
     */
    private Integer id;
    /**
     * 分类id
     */
    private Integer categoryId;
    /**
     * 分类名称
     */
    private String categoryName;
    /**
     * 状态
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
