package org.coastline.one.flink.stream.window;

import com.alibaba.fastjson.JSONObject;
import org.apache.flink.api.common.functions.ReduceFunction;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zouhuajian
 * @date 2020/11/21
 */
public class RuleReduceTimeFunction implements ReduceFunction<JSONObject> {

    private List<JSONObject> cache = new ArrayList<>();

    @Override
    public JSONObject reduce(JSONObject computed, JSONObject data) throws Exception {
        if (cache.size() % 20 ==0) {
            cache.forEach(System.out::println);
            System.out.println("================================================");
        }
        cache.add(data);
        double newData = data.getDoubleValue("value");
        double computedValue = computed.getDoubleValue("value");
        computed.put("value", newData + computedValue);
        computed.put("count", computed.getDoubleValue("count") + 1);
        computed.put("time", data.getString("time"));
        return computed;
    }
}
