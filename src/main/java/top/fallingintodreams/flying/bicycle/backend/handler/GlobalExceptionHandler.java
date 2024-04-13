package top.fallingintodreams.flying.bicycle.backend.handler;

import cn.dev33.satoken.exception.NotLoginException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.fallingintodreams.flying.bicycle.backend.common.ApiResponse;
import top.fallingintodreams.flying.bicycle.backend.common.Constant;
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

    @ExceptionHandler(NotLoginException.class)
    public ApiResponse notLoginException(NotLoginException e) {
        log.error("用户未登录", e);
        return ApiResponse.error(Constant.ResponseCodeEnum.UNAUTHORIZED.getCode(), e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResponse illegalArgumentException(IllegalArgumentException e) {
        log.error("非法参数", e);
        return ApiResponse.error(Constant.ResponseCodeEnum.ILLEGAL_PARAMETERS.getCode(),
                Constant.ResponseCodeEnum.ILLEGAL_PARAMETERS.getMsg());
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse unknownException(Exception e) {
        log.error("未知异常", e);
        return ApiResponse.error(Constant.ResponseCodeEnum.INTERNAL_SERVER_ERROR.getCode(),
                Constant.ResponseCodeEnum.INTERNAL_SERVER_ERROR.getMsg());
    }

}
