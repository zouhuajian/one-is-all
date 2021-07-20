package org.coastline.one.otel.collector.processor;

import org.coastline.one.otel.collector.queue.DataQueue;

/**
 * @author Jay.H.Zou
 * @date 2021/7/20
 */
public interface ProcessorFactor<T> {

    DataProcessor<T> createDefaultProcessor();

    DataProcessor<T> createProcessor(DataQueue<T> nextConsumer);

    DataProcessor<T> createProcessor(DataProcessor<T> nextConsumer);
}
