package org.coastline.one.flink.stream.source;

import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.coastline.common.util.CommonUtil;
import org.coastline.common.util.TimeUtil;
import org.coastline.one.flink.stream.model.MonitorData;

import java.util.concurrent.TimeUnit;

/**
 * @author Jay.H.Zou
 * @date 2021/5/4
 */
public class MonitorDataSourceFunction implements SourceFunction<MonitorData> {

    @Override
    public void run(SourceContext<MonitorData> ctx) throws Exception {
        for (int i = 0; i < 1000000; i++) {
            TimeUnit.MILLISECONDS.sleep(10);
            long currentTime = TimeUtil.getCurrentTime();
            int randomInt = CommonUtil.getRandomInt(200);
            MonitorData data = new MonitorData(currentTime, randomInt, "one");
            ctx.collect(data);
        }
    }

    @Override
    public void cancel() {

    }
}
