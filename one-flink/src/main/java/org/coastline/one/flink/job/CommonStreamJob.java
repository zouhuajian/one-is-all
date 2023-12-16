package org.coastline.one.flink.job;

import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.coastline.one.flink.core.StreamJobExecutor;
import org.coastline.one.flink.core.functions.source.MemorySourceFunction;

import java.io.IOException;

/**
 * @author Jay.H.Zou
 * @date 2021/8/5
 */
public class CommonStreamJob extends StreamJobExecutor {

    private CommonStreamJob(String[] args) throws IOException {
        super(args);
    }

    @Override
    protected void customEnv(StreamExecutionEnvironment env) {
        env.disableOperatorChaining();
        env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC);
        env.getCheckpointConfig().enableUnalignedCheckpoints();
    }

    @Override
    public void buildJob(final StreamExecutionEnvironment env) throws Exception {
        env.addSource(MemorySourceFunction.create()).name("memory_source")
                .rebalance()
                .print().name("default_sink");
    }

    public static void main(String[] args) throws Exception {
        System.out.println(System.getProperty("java.version"));
        CommonStreamJob job = new CommonStreamJob(args);
        job.execute("common_stream");
    }
}
