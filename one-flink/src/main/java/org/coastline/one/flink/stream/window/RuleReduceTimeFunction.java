package org.coastline.one.flink.stream.window;

import com.alibaba.fastjson.JSONObject;
import org.apache.flink.api.common.functions.ReduceFunction;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zouhuajian
 * @date 2020/11/21
 */
public class RuleReduceTimeFunction implements ReduceFunction<JSONObject> {

    private List<JSONObject> cache = new ArrayList<>();

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("mm:ss.SSS");

    @Override
    public JSONObject reduce(JSONObject computed, JSONObject data) throws Exception {
        /*if (cache.size() % 10 == 0) {
            System.out.println(cache);
            System.out.println("================================================");
            cache.clear();
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("source", data.getString("source"));
        jsonObject.put("time",data.getString("time").split(" ")[1]);
        cache.add(jsonObject);*/
        System.out.println(data);
        double newData = data.getDoubleValue("value");
        double computedValue = computed.getDoubleValue("value");
        computed.put("value", newData + computedValue);
        computed.put("count", computed.getDoubleValue("count") + 1);
        computed.put("time", data.getString("time"));
        return computed;
    }
}
