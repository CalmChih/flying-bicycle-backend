package top.fallingintodreams.flying.bicycle.backend.po;

import lombok.Data;

import java.util.Date;

/**
 * 用户信息表
 *
 * @author Chih
 * @date 2024/2/25 19:00
 */
@Data
public class User {

    /**
     * id
     */
    private Long id;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 性别 男1 女0
     */
    private Integer gender;
    /**
     * 生日
     */
    private Date birthday;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 用户状态
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
