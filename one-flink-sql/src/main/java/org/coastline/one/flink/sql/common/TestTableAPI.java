package org.coastline.one.flink.sql.common;

import org.apache.flink.table.api.Slide;
import org.apache.flink.table.api.SlideWithSizeAndSlideOnTimeWithAlias;
import org.apache.flink.table.types.DataType;

import static org.apache.flink.table.api.DataTypes.*;
import static org.apache.flink.table.api.Expressions.$;
import static org.apache.flink.table.api.Expressions.lit;

/**
 * @author Jay.H.Zou
 * @date 2022/7/8
 */
public class TestTableAPI {

    public static void main(String[] args) {
        DataType interval = INTERVAL(DAY(), SECOND());
        SlideWithSizeAndSlideOnTimeWithAlias as = Slide.over(lit(10).minutes())
                .every(lit(5).minutes())
                .on($("rowtime"))
                .as("w");
    }
}
