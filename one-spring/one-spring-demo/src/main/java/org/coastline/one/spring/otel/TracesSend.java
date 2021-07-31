package org.coastline.one.spring.otel;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.protobuf.ByteString;
import com.google.protobuf.UnsafeByteOperations;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.opentelemetry.api.internal.OtelEncodingUtils;
import io.opentelemetry.api.trace.TraceId;
import io.opentelemetry.exporter.otlp.internal.SpanAdapter;
import io.opentelemetry.proto.collector.trace.v1.ExportTraceServiceRequest;
import io.opentelemetry.proto.collector.trace.v1.ExportTraceServiceResponse;
import io.opentelemetry.proto.collector.trace.v1.TraceServiceGrpc;
import io.opentelemetry.sdk.trace.ReadWriteSpan;
import io.opentelemetry.sdk.trace.data.SpanData;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Jay.H.Zou
 * @date 2021/7/29
 */
public class TracesSend {

    private void customSend(ReadWriteSpan span) {
        List<SpanData> spans = Collections.singletonList(span.toSpanData());
        // 构建客户端
        ExportTraceServiceRequest exportTraceServiceRequest = ExportTraceServiceRequest.newBuilder()
                .addAllResourceSpans(SpanAdapter.toProtoResourceSpans(spans))
                .build();
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 4317).usePlaintext().build();

        TraceServiceGrpc.TraceServiceFutureStub exporter = TraceServiceGrpc.newFutureStub(managedChannel);
        Futures.addCallback(
                exporter.export(exportTraceServiceRequest),
                new FutureCallback<ExportTraceServiceResponse>() {
                    @Override
                    public void onSuccess(@Nullable ExportTraceServiceResponse response) {
                        System.out.println("res: " + response);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        System.out.println(t);
                    }
                },
                MoreExecutors.directExecutor());
        managedChannel.shutdown();
    }

    public static void main(String[] args) {
        long INVALID_ID = 0;
        ThreadLocalRandom random = ThreadLocalRandom.current();
        long idHi = random.nextLong();
        long idLo;
        do {
            idLo = random.nextLong();
        } while (idLo == INVALID_ID);
        String traceId = TraceId.fromLongs(idHi, idLo);
        System.out.println(traceId);
        ByteString byteString = UnsafeByteOperations.unsafeWrap(OtelEncodingUtils.bytesFromBase16(traceId, TraceId.getLength()));
        byte[] bytes = byteString.toByteArray();
        char[] chars = new char[32];
        OtelEncodingUtils.bytesToBase16(bytes, chars, bytes.length);
        System.out.println(new String(chars));
    }
}
