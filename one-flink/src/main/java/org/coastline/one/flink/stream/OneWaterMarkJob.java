package org.coastline.one.flink.stream;

import com.alibaba.fastjson.JSONObject;
import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import org.apache.flink.streaming.api.functions.windowing.ProcessAllWindowFunction;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.TimeUnit;

/**
 * @author Jay.H.Zou
 * @date 2020/12/1
 */
public class OneWaterMarkJob {


    public static void main(String[] args) throws Exception {
        System.out.println("job start time: " + LocalDateTime.now().minusDays(1));
        Configuration configuration = new Configuration();
        configuration.setInteger("rest.port", 8002);
        ExecutionConfig config = new ExecutionConfig();
        config.setAutoWatermarkInterval(300);
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(configuration);

        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        env.addSource(new RichSourceFunction<JSONObject>() {

            @Override
            public void run(SourceContext<JSONObject> sourceContext) throws Exception {
                System.out.println("source start time: " + LocalDateTime.now().minusDays(1));
                for (int i = 0; i < 100; i++) {
                    TimeUnit.MILLISECONDS.sleep(200);
                    Instant instant = LocalDateTime.now().minusDays(1).toInstant(ZoneOffset.UTC);
                    long time = instant.toEpochMilli();
                    JSONObject data = new JSONObject();
                    data.put("time", time);
                    /*if (i == 3) {
                        data.put("time", LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli());
                    }*/

                    data.put("format_time", instant.toString());
                    data.put("value", i);
                    sourceContext.collect(data);
                }
            }

            @Override
            public void cancel() {
            }
        })
                .assignTimestampsAndWatermarks(WatermarkStrategy
                        // 参数maxOutOfOrderness就是乱序区间的长度，
                        // 而实际发射的水印为通过覆写extractTimestamp()方法提取出来的时间戳减去乱序区间，
                        // 相当于让水印把步调“放慢一点”。
                        // 这是Flink为迟到数据提供的第一重保障。
                        .<JSONObject>forBoundedOutOfOrderness(Duration.ofMillis(3))
                        // 许用户在配置的时间内（即超时时间内）没有记录到达时将一个流标记为空闲。这样就意味着下游的数据不需要等待水印的到来。
                        //.withIdleness(Duration.ofMinutes(1))
                        .withTimestampAssigner(new SerializableTimestampAssigner<JSONObject>() {

                            @Override
                            public long extractTimestamp(JSONObject data, long recordTimestamp) {
                                return data.getLongValue("time"); //指定EventTime对应的字段
                            }
                        })
                )
                //.keyBy((KeySelector<JSONObject, String>) value -> value.getString("host"), TypeInformation.of(String.class))
                // 设置滑动窗口/滚动窗口，5秒窗口，1秒步长
                .timeWindowAll(Time.seconds(5))

                .reduce(new ReduceFunction<JSONObject>() {
                            @Override
                            public JSONObject reduce(JSONObject jsonObject, JSONObject t1) throws Exception {
                                // System.out.println(jsonObject.toJSONString() + " " + t1.toJSONString());
                                jsonObject.put("count", jsonObject.getIntValue("count") + 1);
                                return jsonObject;
                            }
                        },
                        new ProcessAllWindowFunction<JSONObject, JSONObject, TimeWindow>() {
                            @Override
                            public void process(Context context, Iterable<JSONObject> elements, Collector<JSONObject> out) throws Exception {
                                TimeWindow timeWindow = context.window();
                                long start = timeWindow.getStart();
                                long end = timeWindow.getEnd();
                                System.err.println("sta: " + Instant.ofEpochMilli(start).toString() + "  end: " + Instant.ofEpochMilli(end).toString());
                                System.err.println(elements);
                                out.collect(elements.iterator().next());
                            }
                        })
                .process(new ProcessFunction<JSONObject, JSONObject>() {
                    @Override
                    public void processElement(JSONObject value, Context ctx, Collector<JSONObject> out) throws Exception {
                        System.out.println(value);
                    }
                })
                // 允许窗口延迟销毁，等待1分钟内，如果再有数据进入，则会触发新的计算
                //.allowedLateness(Time.minutes(1))
                // 增量式累加
                /*.reduce(new ReduceFunction<JSONObject>() {
                    @Override
                    public JSONObject reduce(JSONObject computed, JSONObject data) throws Exception {
                        System.out.println("comp: " + computed.toJSONString());
                        System.out.println("data: " + data.toJSONString());
                        return data;
                    }
                }).name("reduce_process")
                // 使用增量式的结果进行计算
                .process(new ProcessFunction<JSONObject, JSONObject>() {
                    @Override
                    public void processElement(JSONObject aggregate, Context context, Collector<JSONObject> collector) throws Exception {
                        long l = context.timerService().currentWatermark();
                    System.out.println(context);
                        System.err.println("aggr: " + aggregate.toJSONString());
                    }
                }).name("process_all_data")*/
                // add sink operator
                .print();

        env.execute("one-rule-time-job");
    }
}
