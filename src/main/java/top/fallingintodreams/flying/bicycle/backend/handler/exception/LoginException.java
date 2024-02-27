package top.fallingintodreams.flying.bicycle.backend.handler.exception;

import top.fallingintodreams.flying.bicycle.backend.handler.enums.IBaseExceptionInfo;

import java.io.Serial;

/**
 * 登录异常
 *
 * @author Chih
 * @date 2024/2/27 20:40
 */
public class LoginException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 6579816802183924539L;

    private String errCode;

    private String errMsg;

    public LoginException() {
        super();
    }

    public LoginException(IBaseExceptionInfo baseExceptionInfo) {
        this.errCode = baseExceptionInfo.getErrCode();
        this.errMsg = baseExceptionInfo.getErrMsg();
    }

    public LoginException(String errMsg, Throwable cause) {
        super(errMsg, cause);
    }

    public String getErrCode() {
        return errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

}
