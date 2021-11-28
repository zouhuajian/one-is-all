package org.coastline.one.flink.stream;

import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.runtime.state.FunctionInitializationContext;
import org.apache.flink.runtime.state.FunctionSnapshotContext;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.checkpoint.CheckpointedFunction;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;
import org.coastline.one.flink.common.model.MonitorData;
import org.coastline.one.flink.stream.core.StreamJobExecutor;
import org.coastline.one.flink.stream.core.process.CommonProcessFunction;
import org.coastline.one.flink.stream.core.source.MemorySourceFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author Jay.H.Zou
 * @date 2021/8/5
 */
public class StateStreamJob extends StreamJobExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(StateStreamJob.class);

    private StateStreamJob(String[] args) throws IOException {
        super(args);
    }

    @Override
    protected void customEnv(StreamExecutionEnvironment env) {
        // 每 1000ms 开始一次 checkpoint
        env.enableCheckpointing(1000);
        CheckpointConfig checkpointConfig = env.getCheckpointConfig();
        // 高级选项：
        // 设置模式为精确一次 (这是默认值)
        checkpointConfig.setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);
        // 确认 checkpoints 之间的时间会进行 500 ms
        checkpointConfig.setMinPauseBetweenCheckpoints(500);
        // Checkpoint 必须在一分钟内完成，否则就会被抛弃
        checkpointConfig.setCheckpointTimeout(60000);
        // 允许两个连续的 checkpoint 错误
        checkpointConfig.setTolerableCheckpointFailureNumber(2);
        // 同一时间只允许一个 checkpoint 进行
        checkpointConfig.setMaxConcurrentCheckpoints(1);
        // 使用 externalized checkpoints，这样 checkpoint 在作业取消后仍就会被保留
        checkpointConfig.enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);
        // 开启实验性的 unaligned checkpoints
        checkpointConfig.enableUnalignedCheckpoints();
    }

    @Override
    public void buildJob(final StreamExecutionEnvironment env) throws Exception {
        env.addSource(MemorySourceFunction.create()).name("memory_source")
                .process(CommonProcessFunction.create()).name("common_process")
                .uid("common_process")
                .keyBy((KeySelector<MonitorData, String>) value -> value.getName())
                .window(TumblingProcessingTimeWindows.of(Time.seconds(10)))
                .process(new AggregateFunction()).name("aggregate")
                .addSink(new SinkFunction<MonitorData>() {
                    @Override
                    public void invoke(MonitorData value, Context context) throws Exception {
                        System.out.println(value);
                    }
                });
    }

    public static void main(String[] args) throws Exception {
        StateStreamJob job = new StateStreamJob(args);
        job.execute("state_stream");
    }

    static class DataKeySelector implements KeySelector<MonitorData, String> {

        private DataKeySelector() {
        }

        public static DataKeySelector create() {
            return new DataKeySelector();
        }

        @Override
        public String getKey(MonitorData value) throws Exception {
            return value.getName();
        }
    }

    static class AggregateFunction extends ProcessWindowFunction<MonitorData, MonitorData, String, TimeWindow> implements CheckpointedFunction {

        @Override
        public void open(Configuration parameters) throws Exception {
        }

        @Override
        public void close() throws Exception {
        }

        @Override
        public void process(String s, ProcessWindowFunction<MonitorData, MonitorData, String, TimeWindow>.Context context, Iterable<MonitorData> elements, Collector<MonitorData> out) throws Exception {

        }

        @Override
        public void snapshotState(FunctionSnapshotContext context) throws Exception {

        }

        @Override
        public void initializeState(FunctionInitializationContext context) throws Exception {

        }
    }
}
