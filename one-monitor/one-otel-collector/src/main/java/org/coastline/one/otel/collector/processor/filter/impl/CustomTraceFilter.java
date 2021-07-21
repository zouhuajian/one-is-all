package org.coastline.one.otel.collector.processor.filter.impl;

import io.opentelemetry.proto.trace.v1.ResourceSpans;
import org.coastline.one.otel.collector.processor.filter.DataFilter;

/**
 * @author Jay.H.Zou
 * @date 2021/7/21
 */
public class CustomTraceFilter implements DataFilter<ResourceSpans> {

    @Override
    public void initialize() throws Exception {
        DataFilter.super.initialize();
    }

    @Override
    public void close() {
        DataFilter.super.close();
    }

    @Override
    public boolean filter(ResourceSpans data) {
        return false;
    }
}
