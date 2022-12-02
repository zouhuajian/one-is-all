package org.coastline.one.flink.stream.functions.source;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;
import org.coastline.one.core.tool.TimeTool;
import org.coastline.one.flink.common.model.MonitorData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author Jay.H.Zou
 * @date 2021/8/29
 */
public class MemorySourceFunction extends RichParallelSourceFunction<MonitorData> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MemorySourceFunction.class);

    private boolean running;
    private Random random;

    public static MemorySourceFunction create() {
        return new MemorySourceFunction();
    }

    @Override
    public void open(Configuration parameters) throws Exception {
        random = new Random(System.currentTimeMillis());
        running = true;
    }

    @Override
    public void run(SourceContext<MonitorData> ctx) throws Exception {
        long index = 0;
        while (running && index < 13) {
            MonitorData data = MonitorData.builder()
                    .index(index++)
                    .time(TimeTool.currentTimeMillis())
                    .service("one-flink")
                    .zone("LOCAL")
                    .name("one-name-" + random.nextInt(100))
                    .duration(0)
                    .build();
            TimeUnit.MILLISECONDS.sleep(200);
            ctx.collect(data);
        }
    }

    @Override
    public void cancel() {
        LOGGER.warn(this.getClass() + " is cancelling...");
        running = false;
    }

    @Override
    public void close() throws Exception {
        // TODO: close resources
        LOGGER.warn(this.getClass() + " is closing...");
    }

}
