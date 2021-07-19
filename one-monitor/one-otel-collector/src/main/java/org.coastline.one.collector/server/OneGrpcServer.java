package org.coastline.one.collector.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.ServerCalls;
import io.grpc.stub.StreamObserver;
import io.opentelemetry.proto.collector.trace.v1.ExportTraceServiceRequest;
import io.opentelemetry.proto.collector.trace.v1.ExportTraceServiceResponse;
import io.opentelemetry.proto.collector.trace.v1.TraceServiceGrpc;
import io.opentelemetry.proto.trace.v1.ResourceSpans;
import org.coastline.one.collector.config.CollectorConfig;

import java.io.IOException;
import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2021/7/17
 */
public class OneGrpcServer {

    // 定义一个Server对象，监听端口来获取rpc请求，以进行下面的处理
    private Server server;

    public static void main(String[] args) throws IOException, InterruptedException {
        final OneGrpcServer serverDemo = new OneGrpcServer();
        //启动server
        serverDemo.start();
        //block 一直到退出程序
        serverDemo.blockUntilShutdown();
    }
    /**
     * 启动一个Server实例，监听client端的请求并处理
     * @throws IOException
     */
    private void start() throws IOException {
        // 给server添加监听端口号，添加 包含业务处理逻辑的类，然后启动
        server = ServerBuilder.forPort(CollectorConfig.DEFAULT_PORT_TRACE)
                .addService(new TraceService())
                .build()
                .start();
        System.out.println("**************** started ****************");
    }
    /**
     * 阻塞server直到关闭程序
     * @throws InterruptedException
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }
    /**
     * proto文件被编译后，在生成的 TraceServiceGrpc 的抽象内部类 TraceServiceImplBase 中包含了 proto 中定义的服务接口的简单实现
     * 该 TraceServiceImplBase 类需要重写这些方法，添加需要的处理逻辑
     */
    static class TraceService extends TraceServiceGrpc.TraceServiceImplBase {

        @Override
        public void export(ExportTraceServiceRequest request, StreamObserver<ExportTraceServiceResponse> responseObserver) {
            System.out.println("new span coming...");
            int resourceSpansCount = request.getResourceSpansCount();
            System.out.println("span count " + resourceSpansCount);
            List<ResourceSpans> resourceSpansList = request.getResourceSpansList();
            System.out.println(resourceSpansList);
            // TODO: data process
            ServerCalls.asyncUnimplementedUnaryCall(TraceServiceGrpc.getExportMethod(), responseObserver);
        }
    }

}
