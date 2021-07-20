package org.coastline.one.otel.collector.receiver;

import org.coastline.one.otel.collector.config.ReceiverConfig;
import org.coastline.one.otel.collector.processor.DataProcessor;


/**
 * build receivers
 *
 * @author Jay.H.Zou
 * @date 2021/7/20
 */
public interface ReceiverFactory<T> {

    DataReceiver createDefaultReceiver();

    DataReceiver createReceivers(ReceiverConfig config, DataProcessor<T> nextConsumer);
}
