package org.coastline.one.otel.collector.queue;

import org.coastline.one.otel.collector.component.DataComponent;

/**
 * @author Jay.H.Zou
 * @date 2021/7/20
 */
public interface DataQueue<T> extends DataComponent {

    boolean add(T data) throws Exception;

    T poll();

    T take() throws Exception;

}
