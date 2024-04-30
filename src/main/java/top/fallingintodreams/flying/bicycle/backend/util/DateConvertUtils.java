package top.fallingintodreams.flying.bicycle.backend.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Date;

public class DateConvertUtils {

    public static String dateFormatToString(Date date) {
        if (date == null) {
            return null;
        }
        return DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static Date formatToDate(String dateStr) {
        if (StringUtils.isBlank(dateStr)) {
            throw new IllegalArgumentException("时间字符串不能为空");
        }
        try {
            return DateUtils.parseDate(dateStr, "yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
