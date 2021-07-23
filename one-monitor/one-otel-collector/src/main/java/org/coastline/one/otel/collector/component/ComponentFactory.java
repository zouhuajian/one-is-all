package org.coastline.one.otel.collector.component;

import org.coastline.one.otel.collector.config.OldCollectorConfig;


/**
 * 构建整个数据处理流程
 *
 * @author Jay.H.Zou
 * @date 2021/7/21
 */
public interface ComponentFactory {


    void buildTraceComponents(OldCollectorConfig config) throws Exception;

    void buildMetricsComponents(OldCollectorConfig config) throws Exception;

}
