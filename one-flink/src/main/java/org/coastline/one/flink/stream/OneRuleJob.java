package org.coastline.one.flink.stream;

import com.alibaba.fastjson.JSONObject;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.collector.selector.OutputSelector;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;
import org.coastline.one.flink.stream.model.AggregateData;
import org.coastline.one.flink.stream.sink.AlertEmailSinkFunction;
import org.coastline.one.flink.stream.sink.AlertWeChatSinkFunction;
import org.coastline.one.flink.stream.sink.PrintSinkFunction;

/**
 * @author zouhuajian
 * @date 2020/11/19
 */
public class OneRuleJob {

    public static void main(String[] args) throws Exception {
        Configuration configuration = new Configuration();
        configuration.setInteger("rest.port", 8002);
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(configuration);
        env.setParallelism(1);
        // add source
        SingleOutputStreamOperator<AggregateData> aggregateDataStream = env.addSource(new NumberSource())
                // source 设置并行为1
                .setParallelism(1)
                // 过滤为空的数据
                .filter(new DataFilterFunction())
                // keyBy, 对不同的实例进行分组
                .keyBy(new KeySelector<JSONObject, String>() {
                    @Override
                    public String getKey(JSONObject value) throws Exception {
                        return value.getString("host");
                    }
                })
                // 设置滑动窗口/滚动窗口
                .timeWindow(Time.seconds(5), Time.seconds(1))
                // 增量式累加
                .reduce(new RuleReduceFunction()).name("reduce_process").setParallelism(2)
                // 使用增量式的结果进行计算
                .process(new RuleProcessFunction()).name("process_all_data").setParallelism(2)
                // TODO: 过滤没有超过阈值的数据
                .filter(new FilterFunction<AggregateData>() {
                    @Override
                    public boolean filter(AggregateData value) throws Exception {
                        return true;
                    }
                });
                /*.split(new OutputSelector<AggregateData>() {

                    @Override
                    public Iterable<String> select(AggregateData value) {
                        return null;
                    }
                })*/

        aggregateDataStream.addSink(new AlertWeChatSinkFunction()).name("weixin");
        aggregateDataStream.addSink(new AlertEmailSinkFunction()).name("email");

        env.execute("influxdb-task");
    }

}
