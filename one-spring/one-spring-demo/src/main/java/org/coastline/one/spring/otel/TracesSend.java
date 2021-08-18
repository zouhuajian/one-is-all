package org.coastline.one.spring.otel;

/**
 * @author Jay.H.Zou
 * @date 2021/7/29
 */
public class TracesSend {

    /*private void customSend(ReadWriteSpan span) {
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
    }*/
}
