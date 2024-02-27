package top.fallingintodreams.flying.bicycle.backend.handler.enums;

/**
 * 异常信息接口
 *
 * @author Chih
 * @date 2024/2/27 21:01
 */
public interface IBaseExceptionInfo {
    /**
     * 获取错误码
     * @return 错误码
     */
    String getErrCode();

    /**
     * 获取错误信息
     * @return 错误信息
     */
    String getErrMsg();
}
