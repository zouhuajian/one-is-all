package org.coastline.common.compare;

import com.alibaba.fastjson.JSONObject;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class JSONTest {

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM-dd HH:mm:ss");

    public static void main(String[] args) {
        long time = 1607000800000L;
        String format = dateTimeFormatter.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneOffset.systemDefault()));
        System.out.println(format);
    }

}
