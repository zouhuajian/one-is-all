package org.coastline.one.otel.collector.processor.filter;

import org.coastline.one.otel.collector.component.DataComponent;

/**
 * @author Jay.H.Zou
 * @date 2021/7/20
 */
public interface DataFilter<T> extends DataComponent {

    boolean filter(T data);
}
