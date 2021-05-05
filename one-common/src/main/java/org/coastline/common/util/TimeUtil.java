package org.coastline.common.util;

import java.time.Instant;

/**
 * @author Jay.H.Zou
 * @date 2021/5/4
 */
public class TimeUtil {

    private TimeUtil(){}


    public static long getCurrentTime() {
        return Instant.now().toEpochMilli();
    }

}
