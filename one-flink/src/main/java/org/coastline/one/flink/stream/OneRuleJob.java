package org.coastline.one.flink.stream;

import com.alibaba.fastjson.JSONObject;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;
import org.coastline.one.flink.stream.filter.CompareFilterFunction;
import org.coastline.one.flink.stream.model.AggregateData;
import org.coastline.one.flink.stream.sink.AlertEmailSinkFunction;
import org.coastline.one.flink.stream.sink.AlertWeChatSinkFunction;
import org.coastline.one.flink.stream.sink.PrintSinkFunction;
import org.coastline.one.flink.stream.source.FakeDataSource;
import org.coastline.one.flink.stream.window.RuleProcessFunction;
import org.coastline.one.flink.stream.window.RuleReduceFunction;

/**
 * @author zouhuajian
 * @date 2020/11/19
 */
public class OneRuleJob {


    static final OutputTag<JSONObject> outputTag = new OutputTag<JSONObject>("save_hbase") {
    };

    public static void main(String[] args) throws Exception {
        Configuration configuration = new Configuration();
        configuration.setInteger("rest.port", 8002);
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(configuration);
        env.setParallelism(1);
        // add source
        SingleOutputStreamOperator<JSONObject> singleOutputStream = env.addSource(new FakeDataSource())
                // source 设置并行为1
                .setParallelism(1).name("fake_data_source")
                .process(new ProcessFunction<JSONObject, JSONObject>() {
                    @Override
                    public void processElement(JSONObject value, Context ctx, Collector<JSONObject> out) throws Exception {
                        out.collect(value);
                        ctx.output(outputTag, value);
                    }
                }).setParallelism(2).name("side_out");

        // 分出一个支流并存储进 HBase/Others
        DataStream<JSONObject> sideOutput = singleOutputStream.getSideOutput(outputTag);
        sideOutput.addSink(new PrintSinkFunction()).setParallelism(4).name("save_hbase");

        // keyBy, host or url or method
        SingleOutputStreamOperator<AggregateData> outputStream = singleOutputStream
                .keyBy((KeySelector<JSONObject, String>) value -> value.getString("host"))
                // 设置滑动窗口/滚动窗口，5秒窗口，1秒步长
                .timeWindow(Time.seconds(5), Time.seconds(1))
                // 增量式累加
                .reduce(new RuleReduceFunction()).setParallelism(2).name("reduce_process")
                // 使用增量式的结果进行计算
                .process(new RuleProcessFunction()).setParallelism(4).name("process_all_data")
                // 过滤没有超过阈值的数据
                .filter(new CompareFilterFunction()).setParallelism(1).name("filter_good");

        // add sink operator
        outputStream.addSink(new AlertWeChatSinkFunction()).name("weixin");
        outputStream.addSink(new AlertEmailSinkFunction()).name("email");

        env.execute("one-rule-job");
    }

}
