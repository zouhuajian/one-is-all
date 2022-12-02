package org.coastline.one.flink.stream.functions.process;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2022/12/2
 */
public class BufferProcessFunction<T> extends ProcessFunction<T, T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BufferProcessFunction.class);

    private List<T> buffer;
    private Collector<T> out;

    @Override
    public void open(Configuration parameters) throws Exception {
        buffer = new ArrayList<>();
    }

    @Override
    public void close() throws Exception {
        LOGGER.warn("Close Trigger: process buffer {}", buffer.size());
        if (!buffer.isEmpty()) {
            for (T t : buffer) {
                out.collect(t);
            }
        }
    }

    @Override
    public void processElement(T value, ProcessFunction<T, T>.Context ctx, Collector<T> out) throws Exception {
        if (this.out == null) {
            this.out = out;
        }
        buffer.add(value);
    }
}
