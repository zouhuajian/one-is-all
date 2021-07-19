package org.coastline.one.collector.client;

import io.opentelemetry.proto.collector.trace.v1.ExportTraceServiceRequest;

/**
 *
 * @author Jay.H.Zou
 * @date 2021/7/17
 */
public class OneGrpcClient {

    public static void main(String[] args) {

        ExportTraceServiceRequest protoRequest =
                ExportTraceServiceRequest.newBuilder()
                        .build();
    }
}
