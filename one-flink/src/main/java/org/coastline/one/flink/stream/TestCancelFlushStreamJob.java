package org.coastline.one.flink.stream;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.coastline.one.flink.stream.functions.StreamJobExecutor;
import org.coastline.one.flink.stream.functions.process.BufferProcessFunction;
import org.coastline.one.flink.stream.functions.sink.FlushBufferSink;
import org.coastline.one.flink.stream.functions.source.MemorySourceFunction;

import java.io.IOException;

/**
 * @author Jay.H.Zou
 * @date 2021/8/5
 */
public class TestCancelFlushStreamJob extends StreamJobExecutor {

    private TestCancelFlushStreamJob(String[] args) throws IOException {
        super(args);
    }

    @Override
    protected void customEnv(StreamExecutionEnvironment env) {
        env.setParallelism(1);
    }

    @Override
    public void buildJob(final StreamExecutionEnvironment env) throws Exception {
        env.addSource(MemorySourceFunction.create()).name("memory_source")
                .process(new BufferProcessFunction<>()).name("process_buffer")
                .addSink(new FlushBufferSink<>()).name("sink");
    }

    public static void main(String[] args) throws Exception {
        TestCancelFlushStreamJob job = new TestCancelFlushStreamJob(args);
        job.execute("TestCancelFlushStreamJob");
    }
}
