package org.coastline.one.flink.stream;

import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.calcite.shaded.com.google.common.collect.Lists;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;
import org.coastline.common.util.TimeUtil;
import org.coastline.one.flink.stream.model.MonitorData;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 如何高效提高窗口处理
 * 窗口后的 ProcessFunction 不做真正处理，而是发给下游，这样能将窗口打散，尽肯能利用到多个并行能力
 *
 * @author Jay.H.Zou
 * @date 2021/5/11
 */
public class OneProcessJob {

    public static void main(String[] args) throws Exception {
        System.out.println("job start time: " + LocalDateTime.now().minusDays(1));
        Configuration configuration = new Configuration();
        configuration.setInteger("rest.port", 8002);
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(configuration);

        env.addSource(new RichSourceFunction<MonitorData>() {

            @Override
            public void run(SourceContext<MonitorData> ctx) throws Exception {
                for (int i = 0; i < 1000000000000L; i++) {
                    TimeUnit.MILLISECONDS.sleep(5);
                    MonitorData data = new MonitorData(TimeUtil.getCurrentTime(), "name_" + i % 4);
                    ctx.collect(data);
                }
            }

            @Override
            public void cancel() {}
        })
                .map(new MapFunction<MonitorData, MonitorData>() {
                    @Override
                    public MonitorData map(MonitorData value) throws Exception {
                        return value;
                    }
                }).setParallelism(8)
                .keyBy((KeySelector<MonitorData, String>) data -> data.getName())
                // 设置滑动窗口/滚动窗口，5秒窗口，1秒步长
                .timeWindow(Time.seconds(1))
                .process(new ProcessWindowFunction<MonitorData, List<MonitorData>, String, TimeWindow>() {
                    @Override
                    public void process(String key, Context context, Iterable<MonitorData> elements, Collector<List<MonitorData>> out) throws Exception {
                        out.collect(Lists.newArrayList(elements));
                    }
                }).name("window_process").setParallelism(16)
                .process(new ProcessFunction<List<MonitorData>, MonitorData>() {
                    @Override
                    public void processElement(List<MonitorData> value, Context ctx, Collector<MonitorData> out) throws Exception {
                        out.collect( value.get(0));
                    }
                }).setParallelism(12)
                .print().setParallelism(8);

        env.execute("one-rule-time-job");
    }
}
