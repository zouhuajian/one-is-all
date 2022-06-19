package org.coastline.one.flink.stream;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.coastline.one.flink.stream.core.StreamJobExecutor;
import org.coastline.one.flink.stream.core.source.MemorySourceFunction;

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
    }

    @Override
    public void buildJob(final StreamExecutionEnvironment env) throws Exception {
        env.addSource(MemorySourceFunction.create()).name("memory_source")
                .print().name("default_sink");
    }

    public static void main(String[] args) throws Exception {
        CommonStreamJob job = new CommonStreamJob(args);
        job.execute("common_stream");
    }
}
