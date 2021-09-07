package org.coastline.one.flink.job.stream.traces;

import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.core.fs.local.LocalFileSystem;
import org.apache.flink.runtime.state.StateBackend;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.triggers.CountTrigger;
import org.coastline.one.flink.core.execute.JobExecutor;
import org.coastline.one.flink.core.model.MonitorData;
import org.coastline.one.flink.core.process.ListWindowProcessFunction;
import org.coastline.one.flink.core.source.MemorySourceFunction;

import java.io.IOException;
import java.net.URI;

/**
 * @author Jay.H.Zou
 * @date 2021/8/5
 */
public class TracesStorageJob extends JobExecutor {


    private TracesStorageJob(String[] args) throws IOException {
        super(args);
    }

    public static TracesStorageJob start(String[] args) throws Exception {
        TracesStorageJob job = new TracesStorageJob(args);
        job.execute("traces_storage");
        return job;
    }

    @Override
    protected void customEnv(StreamExecutionEnvironment env) throws IOException {
        ExecutionConfig config = env.getConfig();
        //env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime);
        config.setAutoWatermarkInterval(0);
        env.setParallelism(1);
        FileSystem fileSystem = LocalFileSystem.get(URI.create(""));
        StateBackend stateBackend = env.getStateBackend();

    }

    @Override
    public void buildJob(final StreamExecutionEnvironment env) throws Exception {
        Configuration configuration = getConfiguration();

        env.addSource(MemorySourceFunction.create()).name("memory_source")
                .keyBy(Math.round(100))
                .keyBy(DataKeySelector.create())
                .window(TumblingProcessingTimeWindows.of(Time.seconds(1)))
                .trigger(CountTrigger.of(10))
                .process(ListWindowProcessFunction.create())
                .print();
    }

    public static void main(String[] args) throws Exception {
        TracesStorageJob.start(args);
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

}
