package org.coastline.one.flink.job;

import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.coastline.one.flink.common.model.MonitorData;
import org.coastline.one.flink.core.StreamJobExecutor;
import org.coastline.one.flink.core.functions.source.MemorySourceFunction;

import java.io.IOException;

/**
 * @author Jay.H.Zou
 * @date 2021/8/5
 */
public class CustomTypeStreamJob extends StreamJobExecutor {

    private CustomTypeStreamJob(String[] args) throws IOException {
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
                // test type
                .map(new MyMap<>(), TypeInformation.of(MonitorData.class))
                .map(new MyMap<MonitorData, MonitorData>())
                .map(value -> value)
                //.returns(MonitorData.class)
                .print().name("default_sink");
    }

    static class MyMap<T, O> implements MapFunction<T, O> {

        @Override
        public O map(T value) throws Exception {
            return (O) value;
        }
    }

    public static void main(String[] args) throws Exception {
        CustomTypeStreamJob job = new CustomTypeStreamJob(args);
        job.execute("common_stream");
    }
}
