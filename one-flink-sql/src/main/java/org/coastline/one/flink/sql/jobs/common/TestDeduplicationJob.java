package org.coastline.one.flink.sql.jobs.common;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.TableResult;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

/**
 * <pre>
 * [1]:TableSourceScan(table=[[default_catalog, default_database, datagen]], fields=[one_sequence, one_id, one_key])
 *  +- [2]:Calc(select=[one_sequence, one_id, one_key, LOCALTIMESTAMP() AS one_time])
 *    +- [3]:WatermarkAssigner(rowtime=[one_time], watermark=[one_time])
 *
 * [5]:Deduplicate(keep=[FirstRow], key=[one_key], order=[ROWTIME])
 *  +- [6]:Calc(select=[one_sequence, one_id, one_key, one_time, 1 AS rownum])
 *    +- [7]:ConstraintEnforcer[NotNullEnforcer(fields=[one_time, rownum])]
 *       +- [7]:StreamRecordTimestampInserter(rowtime field: 3)
 *          +- Sink: Collect table sink
 * </pre>
 * @author Jay.H.Zou
 * @date 2022/7/12
 */
public class TestDeduplicationJob {

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


        tableEnv.executeSql("CREATE TABLE datagen (\n" +
                " one_sequence INT,\n" +
                " one_id INT,\n" +
                " one_key STRING,\n" +
                " one_time AS localtimestamp,\n" +
                " WATERMARK FOR one_time AS one_time\n" +
                ") WITH (\n" +
                " 'connector' = 'datagen',\n" +
                " -- optional options --\n" +
                " 'rows-per-second'='2',\n" +
                " 'fields.one_sequence.kind'='sequence',\n" +
                " 'fields.one_sequence.start'='1',\n" +
                " 'fields.one_sequence.end'='1000',\n" +
                " 'fields.one_id.min'='1',\n" +
                " 'fields.one_id.max'='1000',\n" +
                " 'fields.one_key.length'='1'\n" +
                ")");

        tableEnv.executeSql("CREATE TABLE print_table (\n" +
                " one_sequence INT,\n" +
                " one_id INT,\n" +
                " one_key STRING,\n" +
                " one_time TIMESTAMP(3)\n" +
                ") WITH (\n" +
                " 'connector' = 'print'\n" +
                ")");

        tableEnv.executeSql("INSERT INTO print_table (\n" +
                "    SELECT one_sequence,\n" +
                "           one_id,\n" +
                "           one_key,\n" +
                "           one_time \n" +
                "    FROM (\n" +
                "        SELECT\n" +
                "            one_sequence,\n" +
                "            one_id,\n" +
                "            one_key,\n" +
                "            one_time,\n" +
                "            ROW_NUMBER() OVER (\n" +
                "                PARTITION BY one_key ORDER BY one_time\n" +
                "            ) AS rownum\n" +
                "        FROM datagen\n" +
                "    )\n" +
                "    WHERE rownum = 1\n" +
                ")");

    }
}
