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
        computed.put("count", computed.getDoubleValue("count") + 1);
        /*Long currentTme = data.getLong("time");
        Long lastTime = computed.getLong("time");
        if (lastTime > currentTme) {
            System.err.println("comp: " + computed.getString("source") + " " + computed.getString("time") + " index=" + computed.getInteger("index")
                    + " \ndata: " + data.getString("source") + " " + data.getString("time") + " index=" + data.getInteger("index"));
        }*/
        computed.put("time", data.getString("time"));
        return computed;
    }
}
