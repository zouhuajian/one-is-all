package org.coastline.common.compare;

import com.alibaba.fastjson.JSONObject;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class JSONTest {

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    public static void main(String[] args) {
        String timestamp = "2020-11-13T03:16:12.451Z";
        long time = LocalDateTime.parse(timestamp, dateTimeFormatter).toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        System.out.println(time);
        System.out.println(LocalDateTime.now());
    }

}
