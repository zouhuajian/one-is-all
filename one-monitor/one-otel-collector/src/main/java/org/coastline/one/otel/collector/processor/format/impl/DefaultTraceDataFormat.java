package org.coastline.one.otel.collector.processor.format.impl;

import io.opentelemetry.proto.trace.v1.ResourceSpans;
import org.coastline.one.otel.collector.processor.format.DataFormat;

import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2021/7/21
 */
public class DefaultTraceDataFormat implements DataFormat<ResourceSpans> {
    @Override
    public boolean process(ResourceSpans data) {
        format(data);
        return true;
    }

    @Override
    public void format(ResourceSpans data) {

    }
}
