package org.coastline.one.flink.stream;

import com.alibaba.fastjson.JSONObject;
import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.TimestampAssigner;
import org.apache.flink.api.common.eventtime.TimestampAssignerSupplier;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.ConnectedStreams;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.AscendingTimestampExtractor;
import org.apache.flink.streaming.api.functions.co.CoMapFunction;
import org.apache.flink.streaming.api.functions.co.RichCoMapFunction;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.coastline.one.flink.stream.sink.NullSinkFunction;
import org.coastline.one.flink.stream.source.FakeDataTime2Source;
import org.coastline.one.flink.stream.source.FakeDataTimeSource;
import org.coastline.one.flink.stream.window.RuleProcessFunction;
import org.coastline.one.flink.stream.window.RuleReduceTimeFunction;

import java.time.Duration;

/**
 * 该类主要测试 Event Time 数据处理
 * https://zhuanlan.zhihu.com/p/158951593
 *
 * @author zouhuajian
 * @date 2020/11/19
 */
public class OneEventTimeRuleJob {
    static JSONObject last;

    public static void main(String[] args) throws Exception {
        Configuration configuration = new Configuration();
        configuration.setInteger("rest.port", 8002);
        ExecutionConfig config = new ExecutionConfig();
        config.setAutoWatermarkInterval(300);
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(configuration);

        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        // add source
        SingleOutputStreamOperator<JSONObject> dataSource1 = env.addSource(new FakeDataTimeSource()).name("fake_data_source_1");
        SingleOutputStreamOperator<JSONObject> dataSource2 = env.addSource(new FakeDataTime2Source()).name("fake_data_source_2");
        ConnectedStreams<JSONObject, JSONObject> connect = dataSource1.connect(dataSource2);
        connect.map(new RichCoMapFunction<JSONObject, JSONObject, JSONObject>() {

            @Override
            public JSONObject map1(JSONObject jsonObject) throws Exception {
                int indexOfThisSubtask = getRuntimeContext().getIndexOfThisSubtask();
                jsonObject.put("index", indexOfThisSubtask);
                return jsonObject;
            }

            @Override
            public JSONObject map2(JSONObject jsonObject) throws Exception {
                int indexOfThisSubtask = getRuntimeContext().getIndexOfThisSubtask();
                jsonObject.put("index", indexOfThisSubtask);
                return jsonObject;
            }
        }).setParallelism(6)
                .assignTimestampsAndWatermarks(WatermarkStrategy
                        // 参数maxOutOfOrderness就是乱序区间的长度，
                        // 而实际发射的水印为通过覆写extractTimestamp()方法提取出来的时间戳减去乱序区间，
                        // 相当于让水印把步调“放慢一点”。
                        // 这是Flink为迟到数据提供的第一重保障。
                        .<JSONObject>forBoundedOutOfOrderness(Duration.ofMillis(5))
                        // 许用户在配置的时间内（即超时时间内）没有记录到达时将一个流标记为空闲。这样就意味着下游的数据不需要等待水印的到来。
                        //.withIdleness(Duration.ofMinutes(1))
                        .withTimestampAssigner(new SerializableTimestampAssigner<JSONObject>() {

                            @Override
                            public long extractTimestamp(JSONObject data, long recordTimestamp) {
                                return data.getLongValue("time"); //指定EventTime对应的字段
                            }
                        })
                )
                .setParallelism(4)
                //.keyBy((KeySelector<JSONObject, String>) value -> value.getString("host"), TypeInformation.of(String.class))
                // 设置滑动窗口/滚动窗口，5秒窗口，1秒步长
                .timeWindowAll(Time.milliseconds(10))
                // 允许窗口延迟销毁，等待1分钟内，如果再有数据进入，则会触发新的计算
                //.allowedLateness(Time.minutes(1))
                // 增量式累加
                .reduce(new RuleReduceTimeFunction()).setParallelism(1).name("reduce_process")
                // 使用增量式的结果进行计算
                .process(new RuleProcessFunction()).setParallelism(4).name("process_all_data")
                // add sink operator
                .addSink(new NullSinkFunction()).setParallelism(1).name("null_sink");

        env.execute("one-rule-time-job");
    }

}
