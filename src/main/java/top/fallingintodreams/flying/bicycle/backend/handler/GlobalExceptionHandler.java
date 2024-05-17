package top.fallingintodreams.flying.bicycle.backend.handler;

import cn.dev33.satoken.exception.NotLoginException;
import com.alipay.api.AlipayApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import top.fallingintodreams.flying.bicycle.backend.common.ApiResponse;
import top.fallingintodreams.flying.bicycle.backend.common.Constant;
import top.fallingintodreams.flying.bicycle.backend.handler.exception.BusinessException;
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

    @ExceptionHandler(NoResourceFoundException.class)
    public ApiResponse noResourceFoundException(NoResourceFoundException e) {
        log.error("没有该资源", e);
        return ApiResponse.error(Constant.ResponseCodeEnum.PATH_ERROR.getCode(),
                Constant.ResponseCodeEnum.PATH_ERROR.getMsg());
    }

    @ExceptionHandler(BusinessException.class)
    public ApiResponse businessException(BusinessException e) {
        log.error("业务异常", e);
        return ApiResponse.error(e.getMessage());
    }

    @ExceptionHandler(AlipayApiException.class)
    public ApiResponse noResourceFoundException(AlipayApiException e) {
        log.error("支付宝接口调用异常", e);
        return ApiResponse.error(Constant.ResponseCodeEnum.ALIPAY_API_EXCEPTION.getCode(),
                Constant.ResponseCodeEnum.ALIPAY_API_EXCEPTION.getMsg());
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse unknownException(Exception e) {
        log.error("未知异常", e);
        return ApiResponse.error(Constant.ResponseCodeEnum.INTERNAL_SERVER_ERROR.getCode(),
                Constant.ResponseCodeEnum.INTERNAL_SERVER_ERROR.getMsg());
    }

}
