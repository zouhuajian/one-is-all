package org.coastline.one.core;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * @author zouhuajian
 * @date 2020/12/17
 */
public class TimeTool {

    private static final long serialVersionUID = 1L;

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private TimeTool() {
    }

    public static long getCurrentTime() {
       return Instant.now().toEpochMilli();
    }

    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(getCurrentTime());
    }

    public static String timestampToDateTime(long time) {
        return dateTimeFormatter.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneOffset.ofHours(8)));
    }

    public static String getCurrentTimeFormat() {
        return timestampToDateTime(getCurrentTime());
    }

}
