package org.coastline.one.flink.sql;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableResult;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

/**
 * @author Jay.H.Zou
 * @date 7/16/2020
 */
public class MyTableJob {

    private static String SOURCE_SQL = "CREATE TABLE item_base (" +
            "    `Key` VARCHAR," +
            "    `ItemNumber` VARCHAR," +
            "    `CountryCode` VARCHAR" +
            ") " +
            "WITH (" +
            "    'connector' = 'kafka', " +
            "    'topic' = 'bigdata-test', " +
            "    'properties.group.id' = 'one-flink-sql8',  " +
            "    'scan.startup.mode' = 'earliest-offset'," +
            "    'format' = 'json', " +
            "    'scan.startup.mode' = 'earliest-offset'," +
            "    'properties.bootstrap.servers' = 'xxxx'" +
            ")";

    private static String SINK_PRINT = "CREATE TABLE local_file (" +
            "    `Key` VARCHAR," +
            "    `ItemNumber` VARCHAR," +
            "    `CountryCode` VARCHAR" +
            ") " +
            "WITH (" +
            "    'connector' = 'print'," +
            "    'print-identifier' = 'test-'" +
            ")";

    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment streamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment();

        EnvironmentSettings settings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build();

        StreamTableEnvironment tableEnvironment = StreamTableEnvironment.create(streamExecutionEnvironment, settings);
        TableResult sourceResult = tableEnvironment.executeSql(SOURCE_SQL);
        sourceResult.print();
        TableResult sinkResult = tableEnvironment.executeSql(SINK_PRINT);
        sinkResult.print();
        String insertSql = "INSERT INTO local_file SELECT Key, ItemNumber, CountryCode FROM item_base";
        TableResult insertResult = tableEnvironment.executeSql(insertSql);
        insertResult.print();
        Table table = tableEnvironment.sqlQuery("SELECT * FROM local_file");
        table.execute().print();
        /*try (CloseableIterator<Row> it = result.collect()) {
            while(it.hasNext()) {
                Row row = it.next();
                // handle row
                System.out.println(row);
            }
        }*/


        /*tableEnvironment.createStatementSet();
        TableResult sinkResult = tableEnvironment.executeSql(SINK_PRINT);
        sinkResult.print();

        String insertSql = "INSERT INTO local_file SELECT Key, ItemNumber, CountryCode FROM item_base";
        TableResult insertResult = tableEnvironment.executeSql(insertSql);
        insertResult.print();*/

    }
}