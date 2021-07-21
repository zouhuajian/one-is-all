package org.coastline.one.otel.collector.processor;

import org.coastline.one.otel.collector.component.DataComponent;

/**
 * @author Jay.H.Zou
 * @date 2021/7/20
 */
public interface DataProcessor<T> extends DataComponent {

    boolean process(T data);

}
