package org.coastline.one.otel.collector.queue.impl;

import io.opentelemetry.proto.trace.v1.ResourceSpans;
import org.coastline.one.otel.collector.queue.DataQueue;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

/**
 * @author Jay.H.Zou
 * @date 2021/7/21
 */
public class MemTraceQueue implements DataQueue<ResourceSpans> {

    private BlockingQueue<ResourceSpans> blockingDeque;

    @Override
    public void initialize() {
        blockingDeque = new ArrayBlockingQueue<>(10000);
    }

    @Override
    public void destroy() {

    }

    @Override
    public boolean put(ResourceSpans data) {
        return false;
    }

    @Override
    public boolean put(List<ResourceSpans> dataList) {
        return false;
    }

    @Override
    public ResourceSpans get() {
        return null;
    }

    @Override
    public List<ResourceSpans> getBatch(long batch) {
        return null;
    }
}
