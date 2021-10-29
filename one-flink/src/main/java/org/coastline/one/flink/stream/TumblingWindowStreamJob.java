package org.coastline.one.flink.stream;

import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.coastline.one.flink.common.model.MonitorData;
import org.coastline.one.flink.common.model.WindowData;
import org.coastline.one.flink.stream.core.StreamJobExecutor;
import org.coastline.one.flink.stream.core.process.ListWindowProcessFunction;
import org.coastline.one.flink.stream.core.source.MemorySourceFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Jay.H.Zou
 * @date 2021/8/5
 */
public class TumblingWindowStreamJob extends StreamJobExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(TumblingWindowStreamJob.class);

    private static final AtomicInteger COUNT = new AtomicInteger();

    private TumblingWindowStreamJob(String[] args) throws IOException {
        super(args);
    }

    @Override
    protected void customEnv(StreamExecutionEnvironment env) {
    }

    @Override
    public void buildJob(final StreamExecutionEnvironment env) throws Exception {
        env.addSource(MemorySourceFunction.create(100)).name("memory_source")
                .keyBy(DataKeySelector.create())
                .window(TumblingProcessingTimeWindows.of(Time.seconds(10)))
                .process(ListWindowProcessFunction.create())
                .addSink(new RichSinkFunction<WindowData<MonitorData>>() {
                    @Override
                    public void invoke(WindowData<MonitorData> value, Context context) throws Exception {
                        List<MonitorData> dataList = value.getDataList();
                        COUNT.addAndGet(dataList.size());
                        LOGGER.info(">>>>> index = {}, key = {}, window = {}, delta count = {}, count = {}",
                                getRuntimeContext().getIndexOfThisSubtask(),
                                value.getKey(), value.getWindow(), dataList.size(), COUNT.get());
                    }
                });
    }

    public static void main(String[] args) throws Exception {
        TumblingWindowStreamJob job = new TumblingWindowStreamJob(args);
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
            return value.getName() + value.getTime() / 10000;
        }
    }

}
