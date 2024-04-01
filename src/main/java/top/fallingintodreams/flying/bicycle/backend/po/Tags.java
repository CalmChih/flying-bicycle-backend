package top.fallingintodreams.flying.bicycle.backend.po;

import lombok.Data;

import java.util.Date;

@Data
public class Tags {

    /**
     * 主键id
     */
    private Long id;
    /**
     * 标签id
     */
    private Long tagId;
    /**
     * 标签名称
     */
    private String tagName;
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
