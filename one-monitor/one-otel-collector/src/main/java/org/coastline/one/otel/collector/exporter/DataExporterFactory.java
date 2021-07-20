package org.coastline.one.otel.collector.exporter;

import org.coastline.one.otel.collector.config.ExporterConfig;

/**
 * @author Jay.H.Zou
 * @date 2021/7/20
 */
public interface DataExporterFactory<T> {

    DataExporter<T> createDefaultDataExporter();

    DataExporter<T> createDataExporter(ExporterConfig config);

}
