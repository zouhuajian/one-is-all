package org.coastline.one.otel.collector.exporter;

import org.coastline.one.otel.collector.component.DataComponent;

import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2021/7/20
 */
public interface DataExporter<T> extends DataComponent {

    boolean export(T data);

    boolean export(List<T> dataList);
}
