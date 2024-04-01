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
    ACCOUNT_DOES_NOT_EXIST("103", "账号不存在，请使用验证码登录"),
    /**
     * 邮箱格式错误
     */
    MAIL_FORMAT_ERROR("104", "邮箱格式错误"),
    /**
     * 验证码发送失败
     */
    CAPTCHA_SEND_ERROR("105", "验证码发送失败");

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
