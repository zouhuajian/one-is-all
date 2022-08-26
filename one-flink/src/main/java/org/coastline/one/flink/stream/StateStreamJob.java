package org.coastline.one.flink.stream;

import org.apache.flink.api.common.state.StateTtlConfig;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.contrib.streaming.state.EmbeddedRocksDBStateBackend;
import org.apache.flink.runtime.state.hashmap.HashMapStateBackend;
import org.apache.flink.runtime.state.storage.FileSystemCheckpointStorage;
import org.apache.flink.runtime.state.storage.JobManagerCheckpointStorage;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;
import org.coastline.one.flink.common.model.MonitorData;
import org.coastline.one.flink.stream.core.StreamJobExecutor;
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
        env.enableCheckpointing(10000, CheckpointingMode.EXACTLY_ONCE);
        CheckpointConfig checkpointConfig = env.getCheckpointConfig();
        // state backend: memory
        env.setStateBackend(new HashMapStateBackend());
        // 使用 rocksdb, 并开启增量 checkpoint
        EmbeddedRocksDBStateBackend embeddedRocksDBStateBackend = new EmbeddedRocksDBStateBackend(true);
        //env.setStateBackend(new EmbeddedRocksDBStateBackend(true));
        // checkpoint storage: job manager
        checkpointConfig.setCheckpointStorage(new JobManagerCheckpointStorage(10 * 1024 * 1024));
        // checkpoint storage: hdfs
        // checkpointConfig.setCheckpointStorage(new FileSystemCheckpointStorage("hdfs:///checkpoints-data/"));
        // checkpoint storage: file
        checkpointConfig.setCheckpointStorage(new FileSystemCheckpointStorage(
                "file:///Users/zouhuajian/Jay/project/zouhuajian/one-is-all/one-flink/checkpoint/"));
        checkpointConfig.setExternalizedCheckpointCleanup(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);
        // savepoint
        // env.setDefaultSavepointDirectory(URI.create("hdfs:///checkpoints-data/"));
        // env.setDefaultSavepointDirectory(URI.create("file:///Users/zouhuajian/Jay/project/zouhuajian/one-is-all/one-flink/savepoint"));
    }

    @Override
    public void buildJob(final StreamExecutionEnvironment env) throws Exception {
        env.addSource(MemorySourceFunction.create()).name("memory_source")
                .uid("source_id")
                .keyBy((KeySelector<MonitorData, String>) MonitorData::getName)
                .window(TumblingProcessingTimeWindows.of(Time.seconds(10)))
                .process(new AggregateFunction()).name("aggregate").uid("aggregate_id")
                .addSink(new RichSinkFunction<MonitorData>() {
                    @Override
                    public void invoke(MonitorData value, Context context) throws Exception {
                    }
                }).uid("sink_id");
    }

    public static void main(String[] args) throws Exception {
        StateStreamJob job = new StateStreamJob(args);
        job.execute("state_stream");
    }

    static class AggregateFunction extends ProcessWindowFunction<MonitorData, MonitorData, String, TimeWindow> {

        private transient ValueState<String> state;

        @Override
        public void open(Configuration parameters) throws Exception {
            ValueStateDescriptor<String> valueStateDescriptor = new ValueStateDescriptor<>("name", TypeInformation.of(String.class));
            StateTtlConfig ttlConfig = StateTtlConfig.newBuilder(org.apache.flink.api.common.time.Time.days(1))
                    .cleanupFullSnapshot()
                    .updateTtlOnCreateAndWrite()
                    .cleanupIncrementally(10, true)
                    // default enable
                    //.disableCleanupInBackground()
                    // 在 RocksDB 压缩时清理
                    //.cleanupInRocksdbCompactFilter(1000)
                    .build();
            //valueStateDescriptor.enableTimeToLive(ttlConfig);
            state = getRuntimeContext().getState(valueStateDescriptor);
        }

        @Override
        public void process(String s, ProcessWindowFunction<MonitorData, MonitorData, String, TimeWindow>.Context context,
                            Iterable<MonitorData> elements, Collector<MonitorData> out) throws Exception {
            state.update(s);
            out.collect(elements.iterator().next());
        }

    }
}
