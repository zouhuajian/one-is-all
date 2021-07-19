package org.coastline.one.grpc.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.ServerCalls;
import io.grpc.stub.StreamObserver;
import io.opentelemetry.proto.collector.trace.v1.ExportTraceServiceRequest;
import io.opentelemetry.proto.collector.trace.v1.ExportTraceServiceResponse;
import io.opentelemetry.proto.collector.trace.v1.TraceServiceGrpc;
import org.coastline.one.grpc.onfig.GrpcConfig;

import java.io.IOException;

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

        server = ServerBuilder.forPort(GrpcConfig.SERVER_PORT)
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
     * proto文件被编译后，在生成的HelloGrpc的抽象内部类HelloImplBase中包含了 proto中定义的服务接口的简单实现
     * 该HelloImpl类需要重写这些方法，添加需要的处理逻辑
     */
    static class TraceService extends TraceServiceGrpc.TraceServiceImplBase {
        // proto文件中的sayHello服务接口被编译后，在生成的HelloGrpc的抽象内部类HelloImplBase中有一个简单的实现
        // 因此，在server端需要重写这个方法，添加上相应的逻辑

        @Override
        public void export(ExportTraceServiceRequest request, StreamObserver<ExportTraceServiceResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(TraceServiceGrpc.getExportMethod(), responseObserver);
        }

    }
}
