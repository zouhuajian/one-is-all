package org.coastline.one.flink.stream;

import com.alibaba.fastjson.JSONObject;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;

import java.util.concurrent.TimeUnit;

/**
 * @author zouhuajian
 * @date 2020/11/20
 */
public class NumberSource extends RichSourceFunction<JSONObject> {
    @Override
    public void run(SourceContext<JSONObject> ctx) throws Exception {
        for (int i = 0; i < 10000; i++) {
            TimeUnit.SECONDS.sleep(1);
            JSONObject data = new JSONObject();
            data.put("host", "127.0.0.1");
            data.put("value", 1);
            data.put("time", System.currentTimeMillis() / 1000);
            ctx.collect(data);
        }
    }

    @Override
    public void cancel() {
        System.err.println("wooooo, job cancel...");
    }
}
