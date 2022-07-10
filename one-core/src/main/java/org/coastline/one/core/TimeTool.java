package org.coastline.one.core;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * @author zouhuajian
 * @date 2020/12/17
 */
public class TimeTool {

    private static final long serialVersionUID = 1L;

    public static final DateTimeFormatter DEFAULT_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    private TimeTool() {
    }

    public static long currentTimeMillis() {
        return Instant.now().toEpochMilli();
    }

    /**
     * TODO: 动态获取时区
     * @return
     */
    public static LocalDateTime currentLocalDateTime() {
        return LocalDateTime.ofInstant(Instant.now(), ZoneOffset.ofHours(8));
    }

    public static String currentLocalDateTimeFormat() {
        return currentLocalDateTime().format(DEFAULT_DATE_TIME_FORMATTER);
    }

    public static LocalDateTime toLocalDateTime(long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneOffset.ofHours(8));
    }

    public static String toLocalDateTimeFormat(long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneOffset.ofHours(8)).format(DEFAULT_DATE_TIME_FORMATTER);
    }

    public static long timeNanoToMillis(long timeNano) {
        return TimeUnit.NANOSECONDS.toMillis(timeNano);
    }

    public static long timeNanoToMicros(long timeNano) {
        return TimeUnit.NANOSECONDS.toMicros(timeNano);
    }

    public static long timeMillisToNano(long timeMillis) {
        return TimeUnit.MILLISECONDS.toNanos(timeMillis);
    }

    public static Timestamp currentTimestamp() {
        return new Timestamp(currentTimeMillis());
    }

}
