package org.coastline.one.otel.collector.processor;

/**
 * @author Jay.H.Zou
 * @date 2021/7/20
 */
public interface DataProcessor<T> {

    boolean process(T data);

}
