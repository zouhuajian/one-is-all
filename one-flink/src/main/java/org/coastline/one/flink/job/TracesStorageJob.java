package org.coastline.one.flink.job;

import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.coastline.one.flink.core.execute.JobExecutor;

/**
 * @author Jay.H.Zou
 * @date 2021/8/5
 */
public class TracesStorageJob extends JobExecutor {


    private TracesStorageJob(String[] args) {
        super(args);
    }

    public static TracesStorageJob start(String[] args) throws Exception {
        TracesStorageJob job = new TracesStorageJob(args);
        job.execute("traces_storage");
        return job;
    }

    @Override
    protected void customEnv(StreamExecutionEnvironment env) {
        ExecutionConfig config = env.getConfig();
        //env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime);
        config.setAutoWatermarkInterval(0);
        env.setParallelism(1);
    }

    @Override
    public void buildJob(final StreamExecutionEnvironment env) throws Exception {
        Configuration configuration = getConfiguration();

    }

    public static void main(String[] args) throws Exception {
        TracesStorageJob.start(args);
    }

}
