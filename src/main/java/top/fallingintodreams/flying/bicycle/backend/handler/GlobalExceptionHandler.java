package top.fallingintodreams.flying.bicycle.backend.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.fallingintodreams.flying.bicycle.backend.common.ApiResponse;
import top.fallingintodreams.flying.bicycle.backend.enums.ResponseCodeEnum;
import top.fallingintodreams.flying.bicycle.backend.handler.exception.LoginException;

/**
 * 全局异常处理
 *
 * @author Chih
 * @date 2024/2/27 20:38
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LoginException.class)
    public ApiResponse loginException(LoginException e) {
        log.error("登录注册异常", e);
        return ApiResponse.error(e.getErrCode(), e.getErrMsg());
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse unknownException(Exception e) {
        log.error("未知异常", e);
        return ApiResponse.error(ResponseCodeEnum.INTERNAL_SERVER_ERROR.getCode(),
                ResponseCodeEnum.INTERNAL_SERVER_ERROR.getMsg());
    }

}
