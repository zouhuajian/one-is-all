package org.coastline.one.otel.collector.receiver;

import org.coastline.one.otel.collector.component.DataComponent;
import org.coastline.one.otel.collector.config.ReceiverConfig;
import org.coastline.one.otel.collector.processor.DataProcessor;
import org.coastline.one.otel.collector.queue.DataQueue;

import java.io.IOException;
import java.util.List;

/**
 * 数据接收端
 *
 * @author Jay.H.Zou
 * @date 2021/7/20
 */
public interface DataReceiver<T> extends DataComponent {

    boolean consume(T data);

}
