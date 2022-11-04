package org.coastline.one.common.java;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @author Jay.H.Zou
 * @date 2022/11/2
 */
public class OneTest {
    public static void main(String[] args) {
        long time = 1667376572063L;

        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneOffset.ofHours(8)).plusHours(-1);

        System.out.println(Instant.ofEpochMilli(time).plusSeconds(-3600));
    }
}
