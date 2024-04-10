package top.fallingintodreams.flying.bicycle.backend.common;


import lombok.Data;
import top.fallingintodreams.flying.bicycle.backend.common.Constant.ResponseCodeEnum;

import java.io.Serial;
import java.io.Serializable;

/**
 * 统一返回包装类
 *
 * @author Chih
 * @date 2024/2/25 20:33
 */
@Data
public class ApiResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 2633609520611856010L;

    private String code;
    private String msg;
    private Object data;

    public ApiResponse(String responseCode, String msg) {
        this(responseCode, null, msg);
    }

    public ApiResponse(String responseCode, Object data, String msg) {
        this.code = responseCode;
        this.data = data;
        this.msg = msg;
    }

    public static ApiResponse success() {
        return new ApiResponse(ResponseCodeEnum.SUCCESS.getCode(), ResponseCodeEnum.SUCCESS.getMsg());
    }

    public static ApiResponse success(ResponseCodeEnum responseCode) {
        return new ApiResponse(responseCode.getCode(), responseCode.getMsg());
    }

    public static ApiResponse success(String msg) {
        return new ApiResponse(ResponseCodeEnum.SUCCESS.getCode(), msg);
    }

    public static ApiResponse success(Object data) {
        return new ApiResponse(ResponseCodeEnum.SUCCESS.getCode(), data, ResponseCodeEnum.SUCCESS.getMsg());
    }

    public static ApiResponse success(Object data, String msg) {
        return new ApiResponse(ResponseCodeEnum.SUCCESS.getCode(), data, msg);
    }

    public static ApiResponse success(ResponseCodeEnum responseCode, Object data) {
        return new ApiResponse(responseCode.getCode(), data, responseCode.getMsg());
    }

    public static ApiResponse success(ResponseCodeEnum responseCode, Object data, String msg) {
        return new ApiResponse(responseCode.getCode(), data, msg);
    }

    public static ApiResponse error(String code, String msg) {
        return new ApiResponse(code, msg);
    }

    public static ApiResponse error(String msg) {
        return new ApiResponse(ResponseCodeEnum.FAIL.getCode(), msg);
    }

}
