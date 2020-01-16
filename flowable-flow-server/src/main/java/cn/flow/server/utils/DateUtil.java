package cn.flow.server.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String FORMAT_YYYYMMDD = "yyyyMMdd";

    public static String formatDate(Date date, String format){
        if (date == null)
        {
            return null;
        }
        return new SimpleDateFormat(format).format(date);
    }
}
