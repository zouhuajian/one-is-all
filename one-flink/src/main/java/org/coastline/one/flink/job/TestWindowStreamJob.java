package org.coastline.one.flink.job;

import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;
import org.coastline.one.core.tool.TimeTool;
import org.coastline.one.flink.common.model.MonitorData;
import org.coastline.one.flink.core.StreamJobExecutor;
import org.coastline.one.flink.core.functions.source.MemorySourceFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Jay.H.Zou
 * @date 2021/8/5
 */
public class TestWindowStreamJob extends StreamJobExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestWindowStreamJob.class);

    private static final AtomicInteger COUNT = new AtomicInteger();

    private TestWindowStreamJob(String[] args) throws IOException {
        super(args);
    }

    @Override
    protected void customEnv(StreamExecutionEnvironment env) {
    }

    @Override
    public void buildJob(final StreamExecutionEnvironment env) throws Exception {
        // test
        // checkpoint barrie disable: -1
        env.getCheckpointConfig().setCheckpointInterval(10000);
        env.getCheckpointConfig().enableUnalignedCheckpoints();
        // default value: 200ms
        env.getConfig().setAutoWatermarkInterval(200);
        //env.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.AT_LEAST_ONCE);
        env.addSource(MemorySourceFunction.create()).name("memory_source").uid("kafka")
                .assignTimestampsAndWatermarks(WatermarkStrategy
                        // 参数 maxOutOfOrderness: 迟到数据的的上限
                        .<MonitorData>forBoundedOutOfOrderness(Duration.ofSeconds(10))
                        // 处理空闲数据源, 空闲Duration.ofSeconds(n)后触发
                        .withIdleness(Duration.ofSeconds(30))
                        .withTimestampAssigner(new SerializableTimestampAssigner<MonitorData>() {
                            @Override
                            public long extractTimestamp(MonitorData element, long recordTimestamp) {
                                /*LOGGER.warn("element time = {}, recordTimestamp = {}",
                                        TimeTool.toLocalDateTimeFormat(element.getTime()),
                                        TimeTool.toLocalDateTimeFormat(recordTimestamp));*/
                                return element.getTime();
                            }
                        }))
                .keyBy(DataKeySelector.create())
                /*.countWindow(10)
                .process(new ProcessWindowFunction<MonitorData, MonitorData, String, GlobalWindow>() {
                    @Override
                    public void process(String s, ProcessWindowFunction<MonitorData, MonitorData, String, GlobalWindow>.Context context, Iterable<MonitorData> elements, Collector<MonitorData> out) throws Exception {
                        System.out.println(s+ " = " + Lists.newArrayList(elements).size());
                        out.collect(elements.iterator().next());
                    }
                })*/
                .window(TumblingEventTimeWindows.of(Time.seconds(10)))
                //.window(SlidingEventTimeWindows.of(Time.seconds(10), Time.seconds(2)))
                .process(new ProcessWindowFunction<MonitorData, MonitorData, String, TimeWindow>() {
                    @Override
                    public void process(String key, ProcessWindowFunction<MonitorData, MonitorData, String, TimeWindow>.Context context,
                                        Iterable<MonitorData> elements, Collector<MonitorData> out) throws Exception {
                        long currentWatermark = context.currentWatermark();
                        TimeWindow window = context.window();
                        LOGGER.warn("window start = {}, end = {},current water marker = {}",
                                TimeTool.toLocalDateTimeFormat(window.getStart()),
                                TimeTool.toLocalDateTimeFormat(window.getEnd()),
                                TimeTool.toLocalDateTimeFormat(currentWatermark));
                        out.collect(elements.iterator().next());
                    }

                })
                .print();
    }

    public static void main(String[] args) throws Exception {

        TestWindowStreamJob job = new TestWindowStreamJob(args);
        job.execute("water_marker_test");
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
