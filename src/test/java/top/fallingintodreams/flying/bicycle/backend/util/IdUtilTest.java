package top.fallingintodreams.flying.bicycle.backend.util;

import cn.hutool.core.lang.Snowflake;
import org.junit.jupiter.api.Test;

public class IdUtilTest {

    @Test
    void testGenerateGoodsId() {
        System.out.println(IdUtil.generateGoodsId());
    }

    @Test
    void testGenerateCategoryId() {
        for (int i = 0; i < 50; i++) {
            System.out.println(IdUtil.generateCategoryId());
        }
    }

    @Test
    void testSnow() {
        for (int i = 0; i < 10; i++) {
            Snowflake snowflake = cn.hutool.core.util.IdUtil.getSnowflake();
            System.out.println(snowflake.nextId());//1784577406805843968
        }
    }
}
