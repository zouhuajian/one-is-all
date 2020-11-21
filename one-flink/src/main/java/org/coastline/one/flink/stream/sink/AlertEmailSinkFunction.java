package org.coastline.one.flink.stream.sink;

import com.alibaba.fastjson.JSONObject;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.coastline.one.flink.stream.model.AggregateData;

/**
 * @author zouhuajian
 * @date 2020/11/21
 */
public class AlertEmailSinkFunction implements SinkFunction<AggregateData> {

    @Override
    public void invoke(AggregateData value, Context context) throws Exception {
        // TODO: 如果达到阈值，则发送告警，发送成功与否都存入 DB
        System.err.println("Email: " + JSONObject.toJSONString(value));
    }
}
