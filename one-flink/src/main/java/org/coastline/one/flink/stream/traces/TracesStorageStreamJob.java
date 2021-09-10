package org.coastline.one.flink.stream.traces;

import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.triggers.CountTrigger;
import org.coastline.one.flink.stream.core.StreamJobExecutor;
import org.coastline.one.flink.common.model.MonitorData;
import org.coastline.one.flink.stream.core.process.ListWindowProcessFunction;
import org.coastline.one.flink.stream.core.source.MemorySourceFunction;

import java.io.IOException;

/**
 * @author Jay.H.Zou
 * @date 2021/8/5
 */
public class TracesStorageStreamJob extends StreamJobExecutor {


    private TracesStorageStreamJob(String[] args) throws IOException {
        super(args);
    }

    public static TracesStorageStreamJob start(String[] args) throws Exception {
        TracesStorageStreamJob job = new TracesStorageStreamJob(args);
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

        env.addSource(MemorySourceFunction.create()).name("memory_source")
                .keyBy(Math.round(100))
                .keyBy(DataKeySelector.create())
                .window(TumblingProcessingTimeWindows.of(Time.seconds(1)))
                .trigger(CountTrigger.of(10))
                .process(ListWindowProcessFunction.create())
                .print();
    }

    public static void main(String[] args) throws Exception {
        TracesStorageStreamJob.start(args);
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
