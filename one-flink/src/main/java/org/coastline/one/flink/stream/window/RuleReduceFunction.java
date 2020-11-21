package org.coastline.one.flink.stream.window;

import com.alibaba.fastjson.JSONObject;
import org.apache.flink.api.common.functions.ReduceFunction;

/**
 * @author zouhuajian
 * @date 2020/11/21
 */
public class RuleReduceFunction implements ReduceFunction<JSONObject> {
    @Override
    public JSONObject reduce(JSONObject computed, JSONObject data) throws Exception {
        double newData = data.getDoubleValue("value");
        double computedValue = computed.getDoubleValue("value");
        computed.put("value", newData + computedValue);
        computed.put("count", computed.getDoubleValue("count") + 1);
        computed.put("time", data.getString("time"));
        return computed;
    }
}
