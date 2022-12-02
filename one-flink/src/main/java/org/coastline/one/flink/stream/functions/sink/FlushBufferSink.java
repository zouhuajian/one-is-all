package org.coastline.one.flink.stream.functions.sink;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 在异步Sink时，用户主动停止任务时，需要在 cancel 方法中 flush 数据
 *
 * @author Jay.H.Zou
 * @date 2022/12/2
 */
public class FlushBufferSink<T> extends RichSinkFunction<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FlushBufferSink.class);

    private List<T> buffer;

    @Override
    public void open(Configuration parameters) throws Exception {
        buffer = new ArrayList<>();
    }

    @Override
    public void close() throws Exception {
        LOGGER.warn("Close Trigger: sink buffer {}", buffer.size());
        LOGGER.warn(this.getClass() + " is closing...");

    }

    @Override
    public void invoke(T value, Context context) throws Exception {
        buffer.add(value);
        if (buffer.size() > 20) {
            LOGGER.warn("Count Trigger: sink buffer {}", buffer.size());
            buffer = new ArrayList<>();
        }
    }

    @Override
    public void finish() throws Exception {
        LOGGER.warn("Finish Trigger: sink buffer {}", buffer.size());
    }
}
