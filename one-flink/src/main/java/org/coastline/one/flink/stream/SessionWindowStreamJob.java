package org.coastline.one.flink.stream;

import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.streaming.api.windowing.assigners.ProcessingTimeSessionWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.coastline.one.flink.common.model.MonitorData;
import org.coastline.one.flink.common.model.WindowData;
import org.coastline.one.flink.stream.core.StreamJobExecutor;
import org.coastline.one.flink.stream.core.process.ListWindowProcessFunction;
import org.coastline.one.flink.stream.core.source.MemorySourceFunction;
import org.coastline.one.flink.stream.core.trigger.CountPurgeTrigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2021/8/5
 */
public class SessionWindowStreamJob extends StreamJobExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionWindowStreamJob.class);

    private SessionWindowStreamJob(String[] args) throws IOException {
        super(args);
    }

    @Override
    protected void customEnv(StreamExecutionEnvironment env) {
        env.setParallelism(1);
    }

    @Override
    public void buildJob(final StreamExecutionEnvironment env) throws Exception {
        env.addSource(MemorySourceFunction.create()).name("memory_source")
                .keyBy(DataKeySelector.create())
                .window(ProcessingTimeSessionWindows.withGap(Time.seconds(10)))
                //.window(TumblingProcessingTimeWindows.of(Time.seconds(2)))
                //.trigger(ProcessingTimeoutPurgeTrigger.of(CountTrigger.of(1000), Duration.ofSeconds(1000), false, true))
                .trigger(CountPurgeTrigger.of(5))
                .process(ListWindowProcessFunction.create())
                .addSink(new SinkFunction<WindowData<MonitorData>>() {
                    @Override
                    public void invoke(WindowData<MonitorData> value, Context context) throws Exception {
                        List<MonitorData> dataList = value.getDataList();
                        LOGGER.error(dataList.toString());
                        LOGGER.error(">>>>> window = {}, count = {} \n", value.getWindow(), dataList.size());
                    }
                });
    }

    public static void main(String[] args) throws Exception {
        SessionWindowStreamJob job = new SessionWindowStreamJob(args);
        job.execute("window_testing");
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
