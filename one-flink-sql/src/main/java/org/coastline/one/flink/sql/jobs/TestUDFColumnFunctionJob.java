package org.coastline.one.flink.sql.jobs;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.coastline.one.flink.sql.function.ColumnOneToMoreFunction;

/**
 * @author Jay.H.Zou
 * @date 2023/4/28
 */
public class TestUDFColumnFunctionJob {

    public static void main(String[] args) throws Exception {
        Configuration devConfig = new Configuration();
        devConfig.setInteger("rest.port", 8002);
        StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(devConfig);

        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);
        Configuration configuration = tableEnv.getConfig().getConfiguration();
        // set low-level key-value options
        configuration.setString("table.exec.state.ttl", "1h");
        configuration.setBoolean("state.backend.incremental", true);


        tableEnv.createTemporaryFunction("ColumnOneToMoreFunction", new ColumnOneToMoreFunction());

        tableEnv.executeSql("CREATE TABLE datagen (\n" +
                " one_sequence INT,\n" +
                " one_id INT,\n" +
                " one_key STRING,\n" +
                " one_time AS localtimestamp,\n" +
                " WATERMARK FOR one_time AS one_time\n" +
                ") WITH (\n" +
                " 'connector' = 'datagen',\n" +
                " -- optional options --\n" +
                " 'rows-per-second'='3',\n" +
                " 'fields.one_sequence.kind'='sequence',\n" +
                " 'fields.one_sequence.start'='1',\n" +
                " 'fields.one_sequence.end'='1000',\n" +
                " 'fields.one_id.min'='1',\n" +
                " 'fields.one_id.max'='1000',\n" +
                " 'fields.one_key.length'='1'\n" +
                ")");


        // 把时间字段 yyyy-MM-dd HH:mm:ss.SSS 分解为 yyyy-MM-dd HH:mm:ss 和 SSS
        String query = "SELECT one_sequence, one_id, one_key, time_first, time_second " +
                "   FROM datagen " +
                "   LEFT JOIN LATERAL TABLE(ColumnOneToMoreFunction(DATE_FORMAT(one_time, 'yyyy-MM-dd HH:mm:ss.SSS'))) AS T(time_first, time_second) ON TRUE";

        tableEnv.executeSql(query)
                .print();

    }
}
