package org.coastline.one.flink.stream;

import com.alibaba.fastjson.JSONObject;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.ConnectedStreams;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.streaming.api.functions.co.CoMapFunction;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;
import org.coastline.one.flink.stream.filter.CompareFilterFunction;
import org.coastline.one.flink.stream.model.AggregateData;
import org.coastline.one.flink.stream.sink.AlertEmailSinkFunction;
import org.coastline.one.flink.stream.sink.AlertWeChatSinkFunction;
import org.coastline.one.flink.stream.sink.NullSinkFunction;
import org.coastline.one.flink.stream.sink.PrintSinkFunction;
import org.coastline.one.flink.stream.source.FakeDataSource;
import org.coastline.one.flink.stream.source.FakeDataTime2Source;
import org.coastline.one.flink.stream.source.FakeDataTimeSource;
import org.coastline.one.flink.stream.window.RuleProcessFunction;
import org.coastline.one.flink.stream.window.RuleReduceFunction;
import org.coastline.one.flink.stream.window.RuleReduceTimeFunction;

/**
 * 该类主要测试 Event Time 数据处理
 *
 * @author zouhuajian
 * @date 2020/11/19
 */
public class OneEventTimeRuleJob {


    public static void main(String[] args) throws Exception {
        Configuration configuration = new Configuration();
        configuration.setInteger("rest.port", 8002);
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(configuration);
        env.setParallelism(1);
        //env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        // add source
        SingleOutputStreamOperator<JSONObject> dataSource1 = env.addSource(new FakeDataTimeSource()).name("fake_data_source_1");
        SingleOutputStreamOperator<JSONObject> dataSource2 = env.addSource(new FakeDataTime2Source()).name("fake_data_source_2");
        ConnectedStreams<JSONObject, JSONObject> connect = dataSource1.connect(dataSource2);
        connect.map(new CoMapFunction<JSONObject, JSONObject, JSONObject>() {

            @Override
            public JSONObject map1(JSONObject jsonObject) throws Exception {
                return jsonObject;
            }

            @Override
            public JSONObject map2(JSONObject jsonObject) throws Exception {
                return jsonObject;
            }
        })
                .keyBy((KeySelector<JSONObject, String>) value -> value.getString("host"))
                // 设置滑动窗口/滚动窗口，5秒窗口，1秒步长
                .timeWindow(Time.seconds(1))
                // 增量式累加
                .reduce(new RuleReduceTimeFunction()).setParallelism(1).name("reduce_process")
                // 使用增量式的结果进行计算
                .process(new RuleProcessFunction()).name("process_all_data")
                // add sink operator
                .addSink(new NullSinkFunction()).name("null_sink");

        env.execute("one-rule-time-job");
    }

}
