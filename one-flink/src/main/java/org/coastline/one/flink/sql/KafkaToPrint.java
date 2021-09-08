package org.coastline.one.flink.sql;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.TableResult;

import static org.apache.flink.table.api.Expressions.$;

/**
 * @author Jay.H.Zou
 * @date 2021/9/6
 */
public class KafkaToPrint {

    public static void main(String[] args) {
        EnvironmentSettings settings = EnvironmentSettings
                .newInstance()
                .inStreamingMode()
                .useBlinkPlanner()
                .build();
        TableEnvironment tableEnv = TableEnvironment.create(settings);
        Configuration configuration = tableEnv.getConfig().getConfiguration();
        // kafka source
        tableEnv.executeSql("CREATE TEMPORARY TABLE c_kafka_source (\n" +
                "    `time` BIGINT COMMENT '事件时间',\n" +
                "    `service` STRING COMMENT '服务名',\n" +
                "    `host` STRING COMMENT 'host',\n" +
                "    `type` STRING COMMENT 'type',\n" +
                "    `duration` DOUBLE COMMENT '响应时间'\n" +
                ")  WITH (\n" +
                "   'properties.bootstrap.servers'='xxx:9092',\n" +
                "   'properties.group.id'='flink_sql',\n" +
                "   'scan.startup.mode'='latest-offset',\n" +
                "   'scan.topic-partition-discovery.interval' = '30s',\n" +
                "   'topic'='monitor_cat',\n" +
                "   'connector'='kafka',\n" +
                "   'format'='json',\n" +
                "   'json.fail-on-missing-field' = 'false',\n" +
                "   'json.ignore-parse-errors' = 'true'\n" +
                ")");
        // register an output Table
        tableEnv.executeSql("CREATE TEMPORARY TABLE print_table (\n" +
                "    `time` BIGINT COMMENT '事件时间',\n" +
                "    `service` STRING COMMENT '服务名',\n" +
                "    `host` STRING COMMENT 'host',\n" +
                "    `type` STRING COMMENT 'type',\n" +
                "    `duration` DOUBLE COMMENT '响应时间'\n" +
                ") WITH (\n" +
                "    'connector' = 'print'\n" +
                ")");


        // create a Table object from a Table API query
        Table table1 = tableEnv.from("c_kafka_source").select($("time"), $("service"), $("host"), $("type"), $("duration"));
        // create a Table object from a SQL query
        Table table2 = tableEnv.sqlQuery("SELECT * FROM c_kafka_source");
        // emit a Table API result Table to a TableSink, same for SQL result
        TableResult tableResult = table1.executeInsert("print_table");
        tableResult.print();
    }
}
