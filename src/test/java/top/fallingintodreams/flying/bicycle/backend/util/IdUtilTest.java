package top.fallingintodreams.flying.bicycle.backend.util;

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
}
