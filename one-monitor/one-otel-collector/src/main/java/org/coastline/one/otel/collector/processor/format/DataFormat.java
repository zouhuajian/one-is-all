package org.coastline.one.otel.collector.processor.format;

import org.coastline.one.otel.collector.processor.DataProcessor;

/**
 * @author Jay.H.Zou
 * @date 2021/7/20
 */
public interface DataFormat<T> extends DataProcessor<T> {

    default boolean format(T data) {
        return process(data);
    }
}
