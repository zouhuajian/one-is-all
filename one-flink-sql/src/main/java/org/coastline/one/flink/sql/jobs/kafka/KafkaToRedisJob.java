package org.coastline.one.flink.sql.jobs.kafka;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.DataTypes;
import org.apache.flink.table.api.Schema;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.types.Row;

/**
 * @author Jay.H.Zou
 * @date 2022/7/12
 */
public class KafkaToRedisJob {

    public static void main(String[] args) throws Exception {
        Configuration devConfig = new Configuration();
        devConfig.setInteger("rest.port", 8002);
        StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(devConfig);
        env.setParallelism(1);
        /*EnvironmentSettings settings = EnvironmentSettings
                .newInstance()
                .inStreamingMode()
                //.inBatchMode()
                .build();*/
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);
        Configuration configuration = tableEnv.getConfig().getConfiguration();
        // set low-level key-value options
        configuration.setString("table.exec.state.ttl", "1h");
        configuration.setBoolean("state.backend.incremental", true);
        //configuration.setInteger("table.exec.topn.cache-size", 12345);
        /*configuration.setString("table.exec.mini-batch.enabled", "true");
        configuration.setString("table.exec.mini-batch.allow-latency", "5 s");
        configuration.setString("table.exec.mini-batch.size", "5000");*/

        //tableEnv.executeSql("CREATE TEMPORARY FUNCTION DATA_KIND_MAPPING AS 'com.csoss.processor.sql.function.DataKindFunction'");

        tableEnv.executeSql("CREATE TABLE kafka_source_protostuff (\n" +
                "  `dataKind` INT,\n" +
                "  `proctime` AS PROCTIME(),\n" +
                "   `timeMillis` BIGINT,\n" +
                "   `rt` AS TO_TIMESTAMP_LTZ(`timeMillis`, 3),\n" +
                "   WATERMARK FOR `rt` AS `rt` - INTERVAL '5' SECOND" +
                ") WITH (\n" +
                "  'connector' = 'kafka',\n" +
                "  'topic' = 'csoss_monitor_metrics_v1_cat',\n" +
                "  'scan.startup.mode' = 'latest-offset',\n" +
                "  'properties.bootstrap.servers' = 'xxx:9092',\n" +
                "  'properties.group.id' = 'flink_sql_dev',\n" +
                "  'format' = 'protostuff',\n" +
                "  'protostuff.schema-class' = 'com.csoss.monitor.core.model.MetricData'\n" +
                ")");

        tableEnv.executeSql("CREATE TABLE print_table (\n" +
                "  `dataKind` INT\n" +
                ") WITH (\n" +
                "  'connector' = 'print'\n" +
                ")");

        /*tableEnv.executeSql("INSERT INTO print_table (\n" +
                "  SELECT `dataKind` FROM (\n" +
                "    SELECT `dataKind`,\n" +
                "      ROW_NUMBER() OVER (\n" +
                "        PARTITION BY `dataKind`\n" +
                "        ORDER BY `proctime` DESC\n" +
                "      ) AS rownum\n" +
                "    FROM kafka_source_protostuff)\n" +
                "  WHERE rownum = 1\n" +
                ")");*/
        Table table = tableEnv.sqlQuery("  SELECT `dataKind` FROM (\n" +
                "    SELECT `dataKind`,\n" +
                "      ROW_NUMBER() OVER (\n" +
                "        PARTITION BY `dataKind`\n" +
                "        ORDER BY `proctime` ASC\n" +
                "      ) AS rownum\n" +
                "    FROM kafka_source_protostuff)\n" +
                "  WHERE rownum = 1");
        DataStream<Row> rowDataStream = tableEnv.toChangelogStream(table, Schema.newBuilder()
                        .column("dataKind", DataTypes.INT())
                .build());

        rowDataStream.print();
        env.execute();
    }
}
