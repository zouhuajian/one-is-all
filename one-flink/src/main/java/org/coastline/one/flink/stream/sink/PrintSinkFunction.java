package org.coastline.one.flink.stream.sink;

import com.alibaba.fastjson.JSONObject;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;

/**
 * @author zouhuajian
 * @date 2020/11/21
 */
public class PrintSinkFunction implements SinkFunction<JSONObject> {
    @Override
    public void invoke(JSONObject value, Context context) throws Exception {
        System.out.println("Print: " + JSONObject.toJSONString(value));
    }
}
