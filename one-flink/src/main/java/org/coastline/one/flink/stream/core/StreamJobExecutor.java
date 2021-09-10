package org.coastline.one.flink.stream.core;


import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.coastline.one.flink.common.config.LocalConfigLoader;

/**
 * @author Jay.H.Zou
 * @date 2021/8/4
 */
public abstract class StreamJobExecutor {

    private static final long serialVersionUID = 1L;

    private final StreamExecutionEnvironment env;

    public StreamJobExecutor(String[] args)  {
        Configuration devConfig = new Configuration();
        devConfig.setInteger("rest.port", 8002);
        env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(devConfig);
        // Configuration configuration = loadConfig(args);
        // env.getConfig().setGlobalJobParameters(configuration);
        customEnv(this.env);
    }


    /**
     * custom flink env config for job
     */
    protected void customEnv(final StreamExecutionEnvironment env) {
    }


    /**
     * 执行任务
     *
     * @throws Exception
     */
    protected final void execute(String jobName) throws Exception {
        buildJob(this.env);
        env.execute(jobName);
    }

    public Configuration loadConfig(String[] args) {
        Configuration configuration = ParameterTool.fromArgs(args).getConfiguration();
        Configuration load = (Configuration) LocalConfigLoader.create().load();
        configuration.addAll(load);
        return configuration;
    }

    public Configuration getConfiguration() {
        return (Configuration) env.getConfig().getGlobalJobParameters();
    }

    /**
     * 构建任务
     *
     * @throws Exception
     */
    public abstract void buildJob(final StreamExecutionEnvironment env) throws Exception;

}
