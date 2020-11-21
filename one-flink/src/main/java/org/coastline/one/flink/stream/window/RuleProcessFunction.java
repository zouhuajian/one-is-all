package org.coastline.one.flink.stream.window;

import com.alibaba.fastjson.JSONObject;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;
import org.coastline.one.flink.stream.model.AggregateData;

import java.math.BigDecimal;

/**
 * @author zouhuajian
 * @date 2020/11/21
 */
public class RuleProcessFunction extends ProcessFunction<JSONObject, AggregateData> {
    @Override
    public void processElement(JSONObject value, Context ctx, Collector<AggregateData> out) throws Exception {
        AggregateData aggregateData = value.toJavaObject(AggregateData.class);
        BigDecimal divide = BigDecimal.valueOf(aggregateData.getValue()).divide(BigDecimal.valueOf(aggregateData.getCount()));
        aggregateData.setComputedValue(divide.doubleValue());
        out.collect(aggregateData);
    }
}
