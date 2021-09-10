package org.coastline.one.flink.sql.core;

import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.TableEnvironment;
import org.coastline.one.flink.common.config.LocalConfigLoader;

/**
 * abstract table environment
 *
 * @author Jay.H.Zou
 * @date 2021/9/8
 */
public abstract class SqlJobExecutor {

    private static final long serialVersionUID = 1L;

    private final TableEnvironment tableEnv;

    public SqlJobExecutor(String[] args) {
        Configuration configuration = loadConfig(args);
        EnvironmentSettings settings = EnvironmentSettings
                .newInstance()
                .inStreamingMode()
                .useBlinkPlanner()
                .build();
        configuration.addAll(settings.toConfiguration());
        configuration.setInteger("rest.port", 8002);
        tableEnv = TableEnvironment.create(configuration);

        customEnv(tableEnv);
    }


    /**
     * custom flink env config for job
     */
    protected void customEnv(final TableEnvironment tableEnv) {
    }

    /**
     * 执行任务
     *
     * @throws Exception
     */
    protected final void execute(String jobName) throws Exception {
        buildJob(tableEnv);
        tableEnv.execute(jobName);
    }

    public Configuration loadConfig(String[] args) {
        Configuration configuration = ParameterTool.fromArgs(args).getConfiguration();
        Configuration load = (Configuration) LocalConfigLoader.create().load();
        configuration.addAll(load);
        return configuration;
    }

    public Configuration getConfiguration() {
        return tableEnv.getConfig().getConfiguration();
    }

    /**
     * 构建任务
     *
     * @throws Exception
     */
    public abstract void buildJob(final TableEnvironment tableEnv) throws Exception;

}
