package org.coastline.one.flink.sql;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

/**
 * @author Jay.H.Zou
 * @date 7/16/2020
 */
public class MyTableJob {

    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment streamTableEnvironment = StreamTableEnvironment.create(env);
        Table xTable = streamTableEnvironment.from("X").select();
        streamTableEnvironment.createTemporaryView("xTable", xTable);
    }

}
