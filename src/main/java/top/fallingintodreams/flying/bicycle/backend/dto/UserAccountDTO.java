package top.fallingintodreams.flying.bicycle.backend.dto;

import lombok.Data;

/**
 * 用户信息DTO，接收前端传值
 *
 * @author Chih
 * @date 2024/2/25 20:11
 */
@Data
public class UserAccountDTO {
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 邮箱验证码
     */
    private String captcha;
    /**
     * 登录方式 账号密码1 验证码2
     */
    private Integer type;
}
