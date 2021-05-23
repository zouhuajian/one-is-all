package org.coastline.one.flink.stream;

import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.calcite.shaded.com.google.common.collect.Lists;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
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
                    TimeUnit.MILLISECONDS.sleep(1);
                    MonitorData data = new MonitorData(TimeUtil.getCurrentTime(), "name_" + i % 2);
                    ctx.collect(data);
                }
            }

            @Override
            public void cancel() {
            }
        }).name("one_source")

                .keyBy((KeySelector<MonitorData, String>) data -> data.getName())
                // 设置滑动窗口/滚动窗口，5秒窗口，1秒步长
                .timeWindow(Time.milliseconds(200))
                .process(new ProcessWindowFunction<MonitorData, List<MonitorData>, String, TimeWindow>() {
                    @Override
                    public void process(String key, Context context, Iterable<MonitorData> elements, Collector<List<MonitorData>> out) throws Exception {
                        out.collect(Lists.newArrayList(elements));
                    }
                }).name("pre_process").setParallelism(8)
                // 因为算子链化后不再
                .rebalance()
                .process(new ProcessFunction<List<MonitorData>, MonitorData>() {
                    @Override
                    public void processElement(List<MonitorData> value, Context ctx, Collector<MonitorData> out) throws Exception {
                        int indexOfThisSubtask = getRuntimeContext().getIndexOfThisSubtask();
                        System.out.println(indexOfThisSubtask + " - " + value.get(0));
                    }
                }).setParallelism(8)
                .addSink(new SinkFunction<MonitorData>() {
                    @Override
                    public void invoke(MonitorData value, Context context) throws Exception {

                    }
                }).setParallelism(1);

        env.execute("one-rule-time-job");
    }
}
