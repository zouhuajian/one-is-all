package org.coastline.one.flink.stream.core.source;

import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import org.coastline.one.core.TimeTool;
import org.coastline.one.flink.common.model.MonitorData;

import java.util.Random;

/**
 * @author Jay.H.Zou
 * @date 2021/8/29
 */
public class MemorySourceFunction extends RichSourceFunction<MonitorData> {

    private static final int DATA_COUNT = 100;

    private static final Random RANDOM = new Random();

    private MemorySourceFunction() {}

    public static MemorySourceFunction create() {
        return new MemorySourceFunction();
    }

    @Override
    public void run(SourceContext<MonitorData> ctx) throws Exception {
        for (int i = 0; i < DATA_COUNT; i++) {
            MonitorData data = MonitorData.builder()
                    .time(TimeTool.currentTimeMillis())
                    .service("one-flink")
                    .zone("LOCAL")
                    .name("name-" + RANDOM.nextInt(10))
                    .duration(RANDOM.nextInt(1000))
                    .build();
            ctx.collect(data);
        }
    }

    @Override
    public void cancel() {

    }
}
