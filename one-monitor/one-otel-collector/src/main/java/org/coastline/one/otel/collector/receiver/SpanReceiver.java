package org.coastline.one.otel.collector.receiver;

import io.grpc.BindableService;
import io.grpc.stub.StreamObserver;
import io.opentelemetry.proto.collector.trace.v1.ExportTraceServiceRequest;
import io.opentelemetry.proto.collector.trace.v1.ExportTraceServiceResponse;
import io.opentelemetry.proto.collector.trace.v1.TraceServiceGrpc;
import io.opentelemetry.proto.trace.v1.ResourceSpans;

import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2021/7/20
 */
public class SpanReceiver extends AbstractDataReceiver {

    @Override
    protected BindableService buildService() {
        return new TraceService();
    }

    static class TraceService extends TraceServiceGrpc.TraceServiceImplBase {

        @Override
        public void export(ExportTraceServiceRequest request, StreamObserver<ExportTraceServiceResponse> responseObserver) {
            logger.info("span count = {}", request.getResourceSpansCount());
            List<ResourceSpans> resourceSpansList = request.getResourceSpansList();
            logger.info("data = \n{}", resourceSpansList);

            ExportTraceServiceResponse response = ExportTraceServiceResponse.getDefaultInstance();
            //ServerCalls.asyncUnimplementedUnaryCall(TraceServiceGrpc.getExportMethod(), responseObserver);
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }
}
