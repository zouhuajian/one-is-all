package org.coastline.one.flink.stream;

import com.alibaba.fastjson.JSONObject;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;

/**
 * @author zouhuajian
 * @date 2020/11/19
 */
public class OneRuleJob {

    public static void main(String[] args) throws Exception {
        Configuration configuration = new Configuration();
        configuration.setInteger("rest.port", 8002);
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(configuration);
        // add source
        env.addSource(new NumberSource())
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
                .timeWindow(Time.minutes(1), Time.seconds(10))
                .reduce(new ReduceFunction<JSONObject>() {
                    @Override
                    public JSONObject reduce(JSONObject computed, JSONObject data) throws Exception {
                        double newData = data.getDoubleValue("value");
                        double computedValue = computed.getDoubleValue("value");
                        computed.put("value", newData + computedValue);
                        return computed;
                    }
                })
                .print()
                .setParallelism(2);

        env.execute("influxdb-task");
    }

}