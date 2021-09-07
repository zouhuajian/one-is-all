package org.coastline.one.core;

import java.time.Instant;
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

    public static long currentTimeMillis() {
       return Instant.now().toEpochMilli();
    }


}
