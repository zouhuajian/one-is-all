package org.coastline.one.otel.collector.queue;

import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2021/7/20
 */
public interface DataQueue<T> {

    void initialize();

    void destroy();

    boolean put();

    T get();

    List<T> getBatch(long batch);

}
