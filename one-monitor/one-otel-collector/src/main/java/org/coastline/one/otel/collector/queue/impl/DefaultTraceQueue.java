package org.coastline.one.otel.collector.queue.impl;

import io.opentelemetry.proto.trace.v1.ResourceSpans;
import org.coastline.one.otel.collector.model.TraceModel;
import org.coastline.one.otel.collector.queue.DataQueue;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author Jay.H.Zou
 * @date 2021/7/21
 */
public class DefaultTraceQueue implements DataQueue<TraceModel> {

    private BlockingQueue<ResourceSpans> blockingDeque;

    private DefaultTraceQueue() {}

    public static DefaultTraceQueue create(){
        return new DefaultTraceQueue();
    }

    @Override
    public void initialize() {
        blockingDeque = new ArrayBlockingQueue<>(10000);
    }

    @Override
    public void destroy() {

    }

    @Override
    public boolean put(TraceModel data) {
        return false;
    }

    @Override
    public boolean put(List<TraceModel> dataList) {
        return false;
    }

    @Override
    public TraceModel get() {
        return null;
    }

    @Override
    public List<TraceModel> getBatch(long batch) {
        return null;
    }
}
