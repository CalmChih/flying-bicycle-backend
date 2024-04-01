package top.fallingintodreams.flying.bicycle.backend.common;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Chih
 * @date 2024/2/27 0:43
 */
public class Constant {

    public static final class RedisKey {

        // 验证码前缀 key
        private static final String CAPTCHA_KEY_PREFIX = "captcha:";

        public static String CAPTCHA_KEY_PREFIX(String emailAccount) {
            return CAPTCHA_KEY_PREFIX + emailAccount;
        }

        public static final String CATEGORY_MAP_KEY = "category";

    }

    public static final class UserState {

        /**
         * 正常
         */
        public static final int NORMAL = 1;
        /**
         * 注销
         */
        public static final int DEREGISTER = 0;

    }

    @Getter
    public enum GoodsStateEnum {
        NORMAL(1, "正常"),
        TAKEN_OFF_THE_SHELVES(2, "下架"),
        DELETED(3, "删除");

        private final int code;
        private final String state;

        GoodsStateEnum(int code, String state) {
            this.code = code;
            this.state = state;
        }
    }

    public static final Map<Integer, String> goodsStateMap = new HashMap<>();
    static {
        goodsStateMap.put(GoodsStateEnum.NORMAL.getCode(), GoodsStateEnum.NORMAL.getState());
        goodsStateMap.put(GoodsStateEnum.TAKEN_OFF_THE_SHELVES.getCode(), GoodsStateEnum.TAKEN_OFF_THE_SHELVES.getState());
        goodsStateMap.put(GoodsStateEnum.DELETED.getCode(), GoodsStateEnum.DELETED.getState());
    }

    /**
     * 接口返回状态码
     *
     * @author Chih
     * @date 2024/2/25 20:35
     */
    @Getter
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
        INTERNAL_SERVER_ERROR("500", "服务器内部错误，请稍后再试"),
        /**
         * 非法参数
         */
        ILLEGAL_PARAMETERS("1001", "非法参数");

        private final String code;
        private final String msg;

        ResponseCodeEnum(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }

    }

}
