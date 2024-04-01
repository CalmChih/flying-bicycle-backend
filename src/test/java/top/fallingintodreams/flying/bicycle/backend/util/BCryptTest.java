package top.fallingintodreams.flying.bicycle.backend.util;

import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

/**
 * @author Chih
 * @date 2024/3/4 21:17
 */
public class BCryptTest {

    @Test
    void testBcrypt() {
        System.out.println(BCrypt.hashpw("admin123", BCrypt.gensalt()));
    }
}
