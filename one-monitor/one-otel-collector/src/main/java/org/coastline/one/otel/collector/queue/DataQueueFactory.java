package org.coastline.one.otel.collector.queue;

import org.coastline.one.otel.collector.config.QueueConfig;
import org.coastline.one.otel.collector.exporter.DataExporter;

/**
 * @author Jay.H.Zou
 * @date 2021/7/20
 */
public interface DataQueueFactory<T> {

    DataQueue<T> createDefaultDataQueue();

    DataQueue<T> createDataQueue(QueueConfig config, DataExporter<T> nextConsumer);

}
