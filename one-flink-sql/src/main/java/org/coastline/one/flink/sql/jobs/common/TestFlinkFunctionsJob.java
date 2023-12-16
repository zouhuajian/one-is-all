package org.coastline.one.flink.sql.jobs.common;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
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
 *
 * @author Jay.H.Zou
 * @date 2022/7/12
 */
public class TestFlinkFunctionsJob {

    public static void main(String[] args) throws Exception {
        Configuration devConfig = new Configuration();
        devConfig.setInteger("rest.port", 8002);
        StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(devConfig);
        env.setParallelism(1);

        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);

        tableEnv.executeSql("SELECT SUBSTRING('2023-05-08T10:24:10.563+08:00' FROM 0 FOR 10) AS `dt`")
                .print();
        tableEnv.executeSql("SELECT SUBSTRING('2023-05-08T10:24:10.563+08:00' FROM 12 FOR 2) AS `hour`")
                .print();

    }
}
