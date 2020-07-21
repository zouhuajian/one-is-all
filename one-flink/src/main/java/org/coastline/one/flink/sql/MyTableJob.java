package org.coastline.one.flink.sql;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.StatementSet;
import org.apache.flink.table.api.TableResult;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

import java.sql.Timestamp;

/**
 * @author Jay.H.Zou
 * @date 7/16/2020
 */
public class MyTableJob {

    private static String SOURCE_SQL = "CREATE TABLE item_base (" +
            "    `Key` VARCHAR," +
            "    `ItemNumber` VARCHAR," +
            "    `CountryCode` VARCHAR," +
            "    `Time` VARCHAR" +
            ") " +
            "WITH (" +
            "    'connector.type' = 'kafka', " +
            "    'connector.version' = 'universal', " +
            "    'connector.topic' = 'bigdata-test', " +
            "    'connector.properties.group.id' = 'one-flink-sql',  " +
            "    'connector.startup-mode' = 'earliest-offset'," +
            "    'connector.properties.bootstrap.servers' = 'xxxx'," +
            "    'update-mode' = 'append'," +
            "    'format.type' = 'json', " +
            "    'format.derive-schema' = 'true' " +
            ")";

    private static String SINK_SQL = "CREATE TABLE local_file (" +
            "    `Key` VARCHAR," +
            "    `ItemNumber` VARCHAR," +
            "    `CountryCode` VARCHAR," +
            "    `Time` VARCHAR" +
            ") " +
            "WITH (" +
            "    'connector.type' = 'filesystem', " +
            "    'path'='file:///E:\\test\\flink_test\\', " +
            "    'format.type' = 'json'" +
            ")";

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment streamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment();

        EnvironmentSettings settings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build();

        StreamTableEnvironment tableEnvironment = StreamTableEnvironment.create(streamExecutionEnvironment, settings);
        /*tableEnvironment.useCatalog("one_catalog");
        tableEnvironment.useDatabase("one_database");*/
        DataStreamSource<ItemBase> itemBaseDataStreamSource = streamExecutionEnvironment.addSource(new MySource());
        // TableResult sourceResult = tableEnvironment.executeSql(SOURCE_SQL);
        // sourceResult.print();
        TableResult sinkResult = tableEnvironment.executeSql(SINK_SQL);
        sinkResult.print();
        StatementSet statementSet = tableEnvironment.createStatementSet();
        statementSet.execute().print();

        tableEnvironment.createTemporaryView("item_base", itemBaseDataStreamSource);
        String insertSql = "INSERT INTO local_file SELECT Key, ItemNumber, CountryCode, Time FROM item_base";
        TableResult insertResult = tableEnvironment.executeSql(insertSql);
        insertResult.print();

    }

    public static class MySource implements SourceFunction<ItemBase> {

        String userids[] = {
                "4760858d-2bec-483c-a535-291de04b2247", "67088699-d4f4-43f2-913c-481bff8a2dc5",
                "72f7b6a8-e1a9-49b4-9a0b-770c41e01bfb", "dfa27cb6-bd94-4bc0-a90b-f7beeb9faa8b",
                "aabbaa50-72f4-495c-b3a1-70383ee9d6a4", "3218bbb9-5874-4d37-a82d-3e35e52d1702",
                "3ebfb9602ac07779||3ebfe9612a007979", "aec20d52-c2eb-4436-b121-c29ad4097f6c",
                "e7e896cd939685d7||e7e8e6c1930689d7", "a4b1e1db-55ef-4d9d-b9d2-18393c5f59ee"
        };

        @Override
        public void run(SourceContext<ItemBase> sourceContext) throws Exception {
            while (true) {
                String key = userids[(int) (Math.random() * (userids.length - 1))];
                ItemBase itemBase = new ItemBase();
                itemBase.setKey(key);
                itemBase.setItemNumber(String.valueOf(Math.random() * 100));
                itemBase.setCountryCode("USA");
                itemBase.setTime(String.valueOf(new Timestamp(System.currentTimeMillis())));
                sourceContext.collect(itemBase);
                Thread.sleep(100);
            }
        }

        @Override
        public void cancel() {

        }
    }

    public static class ItemBase implements java.io.Serializable {
        private String Key;

        private String ItemNumber;

        private String CountryCode;

        private String Time;

        public String getKey() {
            return Key;
        }

        public void setKey(String key) {
            Key = key;
        }

        public String getItemNumber() {
            return ItemNumber;
        }

        public void setItemNumber(String itemNumber) {
            ItemNumber = itemNumber;
        }

        public String getCountryCode() {
            return CountryCode;
        }

        public void setCountryCode(String countryCode) {
            CountryCode = countryCode;
        }

        public String getTime() {
            return Time;
        }

        public void setTime(String time) {
            Time = time;
        }
    }
}
