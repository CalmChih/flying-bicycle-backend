package top.fallingintodreams.flying.bicycle.backend.enums;

/**
 * 接口返回状态码
 *
 * @author Chih
 * @date 2024/2/25 20:35
 */
public enum ResponseCodeEnum {
    /**
     * 成功
     */
    SUCCESS("200", "成功"),
    /**
     * 失败
     */
    FAIL("400", "失败"),
    /**
     * 用户未登录
     */
    UNAUTHORIZED("401", "未认证"),
    /**
     * 未知异常
     */
    INTERNAL_SERVER_ERROR("500", "服务器内部错误，请稍后再试");

    private final String code;
    private final String msg;

    private ResponseCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

}
