package top.fallingintodreams.flying.bicycle.backend.util;

import cn.hutool.core.util.RandomUtil;

import java.util.Calendar;
import java.util.Random;

public class IdUtil {

    public static Long generateGoodsId() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int week = calendar.get(Calendar.WEEK_OF_YEAR);
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        StringBuilder idStr = new StringBuilder();
        idStr.append(year - 2024);
        idStr.append(hour);
        idStr.append(String.format("%02d", week));
        idStr.append(String.format("%02d", minute));
        idStr.append(day);
        idStr.append(String.format("%03d", new Random().nextInt(1000)));

        return Long.parseLong(idStr.toString());
    }

    public static Integer generateCategoryId() {
        return RandomUtil.randomInt(10000, 100000, true, false);
    }

    public static Long generateTagId() {
        return RandomUtil.randomLong(10000000, 100000000, true, false);
    }
}
