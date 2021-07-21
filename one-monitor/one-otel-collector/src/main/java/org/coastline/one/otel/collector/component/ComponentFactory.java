package org.coastline.one.otel.collector.component;

import org.coastline.one.otel.collector.config.CollectorConfig;


/**
 * 构建整个数据处理流程
 *
 * @author Jay.H.Zou
 * @date 2021/7/21
 */
public interface ComponentFactory {


    void buildTraceComponents(CollectorConfig config) throws Exception;

    void buildMetricsComponents(CollectorConfig config) throws Exception;

    void buildExtensionComponents(CollectorConfig config) throws Exception;
}
