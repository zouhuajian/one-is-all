package org.coastline.one.flink.stream.core.source;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import org.coastline.one.core.TimeTool;
import org.coastline.one.flink.common.model.MonitorData;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author Jay.H.Zou
 * @date 2021/8/29
 */
public class MemorySourceFunction extends RichSourceFunction<MonitorData> {

    private boolean running;

    public static MemorySourceFunction create() {
        return new MemorySourceFunction();
    }

    @Override
    public void open(Configuration parameters) throws Exception {
        running = true;
    }

    @Override
    public void run(SourceContext<MonitorData> ctx) throws Exception {
        while (running) {
            MonitorData data = MonitorData.builder()
                    .time(TimeTool.currentTimeMillis())
                    .service("one-flink")
                    .zone("LOCAL")
                    .name("one-name")
                    .duration(0)
                    .build();
            TimeUnit.MILLISECONDS.sleep(2000);
            ctx.collect(data);
        }
    }

    @Override
    public void cancel() {
        running = false;
    }
}
