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

        public static final String VALID_GOODS_LIST_PREFIX = "valid:goods:";
        public static String VALID_GOODS_LIST(String reservationStartTime, String reservationEndTime) {
            return VALID_GOODS_LIST_PREFIX + reservationStartTime + "to" + reservationEndTime;
        }

    }

    @Getter
    public enum OrderStateEnum {
        /**
         * 未支付
         */
        UNPAID(0, "待支付"),
        /**
         * 支付成功
         */
        PAID(1, "待取车"),
        /**
         * 已取车
         */
        PICKED_UP(2, "待还车"),
        /**
         * 已还车
         */
        VEHICLES_RETURNED(3, "交易成功"),
        /**
         * 已退款
         */
        REFUNDED(4, "交易退款"),
        /**
         * 已取消
         */
        CANCELLED(5, "交易取消"),
        /**
         * 申请售后
         */
        APPLY_FOR_AFTER_SALES(6, "售后中");

        private final Integer stateCode;
        private final String stateValue;

        OrderStateEnum(Integer stateCode, String stateValue) {
            this.stateCode = stateCode;
            this.stateValue = stateValue;
        }

        private static final Map<Integer, String> outOrderStateMap = new HashMap<>();
        static {
            outOrderStateMap.put(UNPAID.getStateCode(), UNPAID.getStateValue());
            outOrderStateMap.put(PAID.getStateCode(), PAID.getStateValue());
            outOrderStateMap.put(PICKED_UP.getStateCode(), PICKED_UP.getStateValue());
            outOrderStateMap.put(VEHICLES_RETURNED.getStateCode(), VEHICLES_RETURNED.getStateValue());
            outOrderStateMap.put(REFUNDED.getStateCode(), REFUNDED.getStateValue());
            outOrderStateMap.put(CANCELLED.getStateCode(), CANCELLED.getStateValue());
            outOrderStateMap.put(APPLY_FOR_AFTER_SALES.getStateCode(), APPLY_FOR_AFTER_SALES.getStateValue());
        }
        public static String convert(Integer orderState) {
            return outOrderStateMap.get(orderState);
        }

    }

    @Getter
    public enum AfterSalesStateEnum {
        /**
         * 售后中
         */
        WAITING_AFTER_SALES(1, "售后中"),
        /**
         * 售后成功
         */
        AFTER_SALES_SUCCESS(2, "售后成功");

        private final Integer stateCode;
        private final String stateValue;

        AfterSalesStateEnum(Integer stateCode, String stateValue) {
            this.stateCode = stateCode;
            this.stateValue = stateValue;
        }

        private static final Map<Integer, String> outAfterSalesStateMap = new HashMap<>();
        static {
            outAfterSalesStateMap.put(WAITING_AFTER_SALES.getStateCode(), WAITING_AFTER_SALES.getStateValue());
            outAfterSalesStateMap.put(AFTER_SALES_SUCCESS.getStateCode(), AFTER_SALES_SUCCESS.getStateValue());
        }
        public static String convert(Integer afterSalesState) {
            return outAfterSalesStateMap.get(afterSalesState);
        }

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
        ILLEGAL_PARAMETERS("1001", "非法参数"),
        /**
         * 请求路径错误
         */
        PATH_ERROR("1002", "请求路径错误");

        private final String code;
        private final String msg;

        ResponseCodeEnum(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }

    }

}
