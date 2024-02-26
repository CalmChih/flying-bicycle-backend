package top.fallingintodreams.flying.bicycle.backend.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

/**
 * 随机生成用户名
 *
 * @author Chih
 * @date 2024/2/25 21:21
 */
public class RandomStringUtilsTest {

    @Test
    void testRandomNickName() {
        System.out.println(RandomStringUtils.randomAlphanumeric(8));
    }

    @Test
    void testRandomCaptcha() {
        System.out.println(RandomStringUtils.randomNumeric(6));
        System.out.println(RandomStringUtils.randomGraph(8));
        System.out.println(RandomStringUtils.randomAscii(8));
        System.out.println(RandomStringUtils.randomAlphabetic(8));
        System.out.println(RandomStringUtils.randomAlphanumeric(16));
    }

}
