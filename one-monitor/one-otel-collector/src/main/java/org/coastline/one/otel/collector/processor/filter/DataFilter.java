package org.coastline.one.otel.collector.processor.filter;

import org.coastline.one.otel.collector.processor.DataProcessor;

/**
 * @author Jay.H.Zou
 * @date 2021/7/20
 */
public interface DataFilter<T> extends DataProcessor<T> {

    boolean filter(T t);
}
