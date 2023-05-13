package org.coastline.one.core.test.tool;

import org.junit.Test;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

/**
 * @author Jay.H.Zou
 * @date 2023/3/7
 */
public class TestTimeTool {

    @Test
    public void getLastMonthStartAndEnd() {
        String dateStr = "2023-01-20";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateStr, formatter);
        System.out.println(localDate);
        System.out.println("---");
        LocalDate now = LocalDate.now();
        LocalDate date = now.plusMonths(-1);
        LocalDate firstDay = date.with(TemporalAdjusters.firstDayOfMonth()); // 获取当前月的第一天
        LocalDate lastDay = date.with(TemporalAdjusters.lastDayOfMonth()); // 获取当前月的最后一天
        System.out.println(now);
        System.out.println(firstDay);
        System.out.println(lastDay);
    }


    @Test
    public void testDateTimeToTimestamp() {
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
        System.out.println(timestamp.getTime());
        System.out.println("yyyy-MM-dd HH:mm:ss".substring(11, 13));
    }
}
