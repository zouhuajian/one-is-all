package org.coastline.one.otel.collector.processor.filter.impl;

import io.opentelemetry.proto.trace.v1.ResourceSpans;
import org.coastline.one.otel.collector.processor.filter.DataFilter;

/**
 * @author Jay.H.Zou
 * @date 2021/7/21
 */
public class DefaultTraceFilter implements DataFilter<ResourceSpans> {

    @Override
    public boolean process(ResourceSpans data) {
        return filter(data);
    }

    @Override
    public boolean filter(ResourceSpans data) {
        return true;
    }
}
