package top.fallingintodreams.flying.bicycle.backend.util;

import java.util.regex.Pattern;

/**
 * 正则匹配工具类
 *
 * @author Chih
 * @date 2024/2/26 21:44
 */
public class RegexUtils {

    public static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,}$";
    public static final String CAPTCHA_REGEX = "^\\d{6}$";

    public static boolean matchEmail(String email) {
        return Pattern.matches(EMAIL_REGEX, email);
    }

    public static boolean matchCaptcha(String captcha) {
        return Pattern.matches(CAPTCHA_REGEX, captcha);
    }

}
