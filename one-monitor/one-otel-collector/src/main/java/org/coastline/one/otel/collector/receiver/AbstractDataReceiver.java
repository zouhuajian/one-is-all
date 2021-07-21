package org.coastline.one.otel.collector.receiver;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.coastline.one.otel.collector.config.ReceiverConfig;
import org.coastline.one.otel.collector.processor.DataProcessor;
import org.coastline.one.otel.collector.queue.DataQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 数据接收端
 *
 * @author Jay.H.Zou
 * @date 2021/7/20
 */
public abstract class AbstractDataReceiver<T> implements DataReceiver<T> {

    protected static final Logger logger = LoggerFactory.getLogger(AbstractDataReceiver.class);

    private ReceiverConfig config;

    private List<DataProcessor<T>> processors;

    private DataQueue<T> dataQueue;

    /**
     * 定义一个Server对象，监听端口来获取rpc请求，以进行下面的处理
     */
    private Server server;

    public AbstractDataReceiver(ReceiverConfig config, List<DataProcessor<T>> processors, DataQueue<T> dataQueue) {
        this.config = config;
        this.processors = processors;
        this.dataQueue = dataQueue;
    }


    @Override
    public void initialize() throws Exception {
        server = ServerBuilder.forPort(config.getPort())
                .addService(buildService())
                .build()
                .start();
    }

    @Override
    public boolean consume(T data) {
        for (DataProcessor<T> processor : processors) {
            if (!processor.process(data)) {
                return false;
            }
        }
        return dataQueue.put(data);
    }

    /**
     * proto文件被编译后，在生成的 TraceServiceGrpc 的抽象内部类 TraceServiceImplBase 中包含了 proto 中定义的服务接口的简单实现
     * 该 TraceServiceImplBase 类需要重写这些方法，添加需要的处理逻辑
     *
     * @return service
     */
    protected abstract BindableService buildService();

    @Override
    public void close() {
        if (server != null) {
            try {
                server.awaitTermination();
            } catch (Exception e) {
                logger.error("close server error", e);
            }
        }
    }


}
