package org.coastline.one.flink.stream;

import com.alibaba.fastjson.JSONObject;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * @author zouhuajian
 * @date 2020/11/20
 */
public class NumberSource extends RichSourceFunction<JSONObject> {

    private transient static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    @Override
    public void run(SourceContext<JSONObject> ctx) throws Exception {
        for (int i = 0; i < 10000; i++) {
            TimeUnit.MILLISECONDS.sleep(500);
            JSONObject data = new JSONObject();
            if (i % 2 == 0) {
                data.put("host", "127.0.0.1");
                data.put("value", 1);
            } else {
                data.put("host", "127.0.0.2");
                data.put("value", 22);
            }

            String time = dateTimeFormatter.format(LocalDateTime.now());
            data.put("time", time);
            data.put("count", 1);
            ctx.collect(data);
        }
    }

    @Override
    public void cancel() {
        System.err.println("wooooo, job cancel...");
    }
}
