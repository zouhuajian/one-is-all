package org.coastline.one.otel.collector.queue.impl;

import org.coastline.one.otel.collector.model.TraceModel;
import org.coastline.one.otel.collector.queue.DataQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author Jay.H.Zou
 * @date 2021/7/21
 */
public final class DefaultTraceQueue implements DataQueue<TraceModel> {

    private static final Logger logger = LoggerFactory.getLogger(DefaultTraceQueue.class);

    private static final int DEFAULT_SIZE = 10000;

    private BlockingQueue<TraceModel> blockingQueue;

    private DefaultTraceQueue() {
    }

    public static DefaultTraceQueue create() throws Exception {
        DefaultTraceQueue defaultTraceQueue = new DefaultTraceQueue();
        defaultTraceQueue.start();
        return defaultTraceQueue;
    }

    @Override
    public void initialize() {
        blockingQueue = new ArrayBlockingQueue<>(DEFAULT_SIZE);
    }

    @Override
    public void close() {
        if (blockingQueue != null) {
            blockingQueue.clear();
        }
        logger.info("default trace queue closed");
    }

    @Override
    public boolean add(TraceModel data) {
        return blockingQueue.add(data);
    }

    @Override
    public TraceModel poll() {
        return blockingQueue.poll();
    }

    @Override
    public TraceModel take() throws Exception {
        return blockingQueue.take();
    }

}
