package org.coastline.one.flink.sql.jobs;

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
public class HudiToClickhouseJob {

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
        /* Configuration configuration = tableEnv.getConfig().getConfiguration();
        // set low-level key-value options
        configuration.setString("table.exec.state.ttl", "1h");
        configuration.setBoolean("state.backend.incremental", true);*/

        //tableEnv.executeSql("CREATE TEMPORARY FUNCTION DATA_KIND_MAPPING AS 'com.csoss.processor.sql.function.DataKindFunction'");

        tableEnv.executeSql("CREATE TABLE metric_meta_hudi (\n" +
                "    appname STRING,\n" +
                "    env_name STRING,\n" +
                "    datakind INT,\n" +
                "    datakind_name STRING,\n" +
                "    metric_type INT,\n" +
                "    metric_type_name STRING,\n" +
                "    metric_name STRING,\n" +
                "    description STRING,\n" +
                "    unit STRING,\n" +
                "    attributes MAP<STRING, STRING>,\n" +
                "    sdk_version STRING,\n" +
                "    creation_time TIMESTAMP(3),\n" +
                "    creatiton_date STRING,\n" +
                "    PRIMARY KEY (appname, env_name, datakind, metric_type, metric_name) NOT ENFORCED\n" +
                ")\n" +
                "PARTITIONED BY (creatiton_date)\n" +
                "WITH (\n" +
                "  'connector' = 'hudi',\n" +
                "  'path' = 'hdfs://xxx:4007/csoss/metrics/metric_meta',\n" +
                "  'table.type' = 'COPY_ON_WRITE',\n" +
                "  'read.streaming.enabled' = 'true',\n" +
                "  'read.streaming.start-commit' = '20220316134557',\n" +
                "  'read.streaming.check-interval' = '3'\n" +
                ")");

        tableEnv.executeSql("CREATE TABLE metric_meta_clickhouse (\n" +
                "    appname STRING,\n" +
                "    env_name STRING,\n" +
                "    datakind INT,\n" +
                "    datakind_name STRING,\n" +
                "    metric_type INT,\n" +
                "    metric_type_name STRING,\n" +
                "    metric_name STRING,\n" +
                "    description STRING,\n" +
                "    unit STRING,\n" +
                "    attributes MAP<STRING, STRING>,\n" +
                "    sdk_version STRING,\n" +
                "    creation_time TIMESTAMP_LTZ(3)\n" +
                "    --PRIMARY KEY (appname, env_name, datakind, metric_type, metric_name) NOT ENFORCED\n" +
                ") WITH (\n" +
                "    'connector' = 'clickhouse',\n" +
                "    'url' = 'clickhouse://xxx:8123',\n" +
                "    'database-name' = 'monitor',\n" +
                "    'table-name' = 'metric_meta',\n" +
                "    'sink.batch-size' = '1000',\n" +
                "    'sink.flush-interval' = '5s',\n" +
                "    'sink.max-retries' = '3'\n" +
                ")");


        Table table = tableEnv.sqlQuery("INSERT INTO metric_meta_clickhouse (\n" +
                "    SELECT \n" +
                "        appname,\n" +
                "        env_name,\n" +
                "        datakind,\n" +
                "        datakind_name,\n" +
                "        metric_type,\n" +
                "        metric_type_name,\n" +
                "        metric_name,\n" +
                "        description,\n" +
                "        unit,\n" +
                "        attributes,\n" +
                "        sdk_version,\n" +
                "        CURRENT_TIMESTAMP AS creation_time\n" +
                "    FROM metric_meta_hudi\n" +
                ")");
        DataStream<Row> rowDataStream = tableEnv.toChangelogStream(table, Schema.newBuilder()
                        .column("dataKind", DataTypes.INT())
                .build());

        rowDataStream.print();
        env.execute();
    }
}
