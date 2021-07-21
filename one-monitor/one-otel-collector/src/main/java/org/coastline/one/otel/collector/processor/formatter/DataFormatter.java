package org.coastline.one.otel.collector.processor.formatter;

import org.coastline.one.otel.collector.component.DataComponent;

/**
 * @author Jay.H.Zou
 * @date 2021/7/20
 */
public interface DataFormatter<I, O> extends DataComponent {

    O format(I data);
}
