package org.coastline.one.flink.stream.core.source;

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

    private int dataCount;

    private static final Random RANDOM = new Random();

    private MemorySourceFunction() {
        this.dataCount = Integer.MAX_VALUE;
    }

    private MemorySourceFunction(int dataCount) {
        this.dataCount = dataCount;
    }

    public static MemorySourceFunction create() {
        return new MemorySourceFunction();
    }

    public static MemorySourceFunction create(int dataCount) {
        return new MemorySourceFunction(dataCount);
    }

    @Override
    public void run(SourceContext<MonitorData> ctx) throws Exception {
        for (int i = 0; i < dataCount; i++) {
            MonitorData data = MonitorData.builder()
                    .time(TimeTool.currentTimeMillis())
                    .service("one-flink")
                    .zone("LOCAL")
                    //.name("name-" + RANDOM.nextInt(10))
                    .name("one-name")
                    .duration(RANDOM.nextInt(1000))
                    .build();
            TimeUnit.MILLISECONDS.sleep(600);
            ctx.collect(data);
        }
        TimeUnit.MINUTES.sleep(2);
    }

    @Override
    public void cancel() {

    }
}
