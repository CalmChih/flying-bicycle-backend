package top.fallingintodreams.flying.bicycle.backend.handler.enums.impl;

import top.fallingintodreams.flying.bicycle.backend.handler.enums.IBaseExceptionInfo;

/**
 * @author Chih
 * @date 2024/2/27 21:05
 */
public enum LoginEnum implements IBaseExceptionInfo {
    /**
     * 验证码错误
     */
    CAPTCHA_ERROR("101", "验证码错误"),
    /**
     * 账号或密码错误
     */
    WRONG_ACCOUNT_NUMBER_OR_PASSWORD("102", "账号或密码错误"),
    /**
     * 账号不存在，请使用验证码登录
     */
    USER_ACCOUNT_NOT_EXIST("103", "用户账号不存在，请使用验证码登录"),
    /**
     * 邮箱格式错误
     */
    MAIL_FORMAT_ERROR("104", "邮箱格式错误"),
    NOT_ADMIN_ACCOUNT("106", "非管理员账户"),
    /**
     * 验证码发送失败
     */
    CAPTCHA_SEND_ERROR("105", "验证码发送失败"),
    ACCOUNT_OR_PASSWORD_IS_BLANK("106", "账号或密码不能为空"),
    ADMIN_ACCOUNT_NOT_EXITS("107", "账号不存在");

    private final String errCode;
    private final String errMsg;

    LoginEnum(String errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    @Override
    public String getErrCode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }
}
