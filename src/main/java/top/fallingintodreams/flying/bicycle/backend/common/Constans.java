package top.fallingintodreams.flying.bicycle.backend.common;

/**
 * @author Chih
 * @date 2024/2/27 0:43
 */
public class Constans {

    public static final class RedisKey {

        // 验证码前缀 key
        private static final String CAPTCHA_KEY_PREFIX = "captcha:";

        public static String CAPTCHA_KEY_PREFIX(String emailAccount) {
            return CAPTCHA_KEY_PREFIX + emailAccount;
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

}
