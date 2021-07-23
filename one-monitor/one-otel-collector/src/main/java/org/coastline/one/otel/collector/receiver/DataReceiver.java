package org.coastline.one.otel.collector.receiver;

import org.coastline.one.otel.collector.component.DataComponent;

/**
 * 数据接收端
 *
 * @author Jay.H.Zou
 * @date 2021/7/20
 */
public interface DataReceiver<I, O> extends DataComponent {

    boolean consume(I data) throws Exception;

}
