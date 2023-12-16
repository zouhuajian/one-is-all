package org.coastline.one.flink.sql.jobs;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

/**
 * @author Jay.H.Zou
 * @date 2023/12/16
 */
public class TableJobExecutor {

    public static TableEnvironment buildTableEnv() {
        Configuration devConfig = new Configuration();
        devConfig.setInteger("rest.port", 8002);
        StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(devConfig);
        env.setParallelism(1);
        EnvironmentSettings settings = EnvironmentSettings
                .newInstance()
                .inStreamingMode()
                .build();
        return StreamTableEnvironment.create(env, settings);
    }
}
