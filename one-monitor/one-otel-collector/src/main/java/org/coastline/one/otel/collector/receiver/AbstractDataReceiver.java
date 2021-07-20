package org.coastline.one.otel.collector.receiver;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.coastline.one.otel.collector.config.ReceiverConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 数据接收端
 *
 * @author Jay.H.Zou
 * @date 2021/7/20
 */
public abstract class AbstractDataReceiver implements DataReceiver {

    protected static final Logger logger = LoggerFactory.getLogger(AbstractDataReceiver.class);

    /**
     * 定义一个Server对象，监听端口来获取rpc请求，以进行下面的处理
     */
    private Server server;

    @Override
    public void start(ReceiverConfig config) throws IOException {
        // 给server添加监听端口号，添加 包含业务处理逻辑的类，然后启动
        server = ServerBuilder.forPort(config.getPort())
                .addService(buildService())
                .build()
                .start();
        logger.info("{} started at port {}", config.getDataSourceType().getName(), config.getPort());
    }

    /**
     * proto文件被编译后，在生成的 TraceServiceGrpc 的抽象内部类 TraceServiceImplBase 中包含了 proto 中定义的服务接口的简单实现
     * 该 TraceServiceImplBase 类需要重写这些方法，添加需要的处理逻辑
     *
     * @return service
     */
    protected abstract BindableService buildService();

    @Override
    public void shutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }
}
