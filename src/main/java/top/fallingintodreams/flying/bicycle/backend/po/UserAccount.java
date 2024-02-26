package top.fallingintodreams.flying.bicycle.backend.po;

import lombok.Data;

import java.util.Date;

/**
 *
 * 用户账户表
 *
 * @author Chih
 * @date 2024/2/25 19:29
 */
@Data
public class UserAccount {

    /**
     * 自增id
     */
    private Long id;
    /**
     * 账号
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 账号状态
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
