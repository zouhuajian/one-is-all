package org.coastline.one.flink.stream.window;

import com.alibaba.fastjson.JSONObject;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;
import org.coastline.one.flink.stream.model.AggregateData;

/**
 * @author zouhuajian
 * @date 2020/11/21
 */
public class RuleProcessFunction extends ProcessFunction<JSONObject, AggregateData> {
    JSONObject last;

    @Override
    public void processElement(JSONObject value, Context ctx, Collector<AggregateData> out) throws Exception {
        if (last != null) {
            Long currentTme = value.getLong("time");
            Long lastTime = last.getLong("time");
            if (lastTime > currentTme) {
                System.err.println("last: " + last.getString("source") + " " + last.getString("time") + " index=" + last.getInteger("index")
                        + " \ndata: " + value.getString("source") + " " + value.getString("time") + " index=" + value.getInteger("index"));
            }
        }
        last = value;
        AggregateData aggregateData = value.toJavaObject(AggregateData.class);
        out.collect(aggregateData);
    }
}
