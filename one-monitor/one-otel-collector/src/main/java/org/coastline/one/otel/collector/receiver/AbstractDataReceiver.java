package org.coastline.one.otel.collector.receiver;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.coastline.one.otel.collector.config.ReceiverConfig;
import org.coastline.one.otel.collector.processor.filter.DataFilter;
import org.coastline.one.otel.collector.processor.formatter.DataFormatter;
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
public abstract class AbstractDataReceiver<I, O> implements DataReceiver<I, O> {

    protected static final Logger logger = LoggerFactory.getLogger(AbstractDataReceiver.class);

    private ReceiverConfig config;

    private DataFormatter<I, O> formatter;

    private List<DataFilter<O>> filters;

    private DataQueue<O> dataQueue;

    /**
     * 定义一个Server对象，监听端口来获取rpc请求，以进行下面的处理
     */
    private Server server;

    public AbstractDataReceiver(ReceiverConfig config,
                                DataFormatter<I, O> formatter,
                                List<DataFilter<O>> filters,
                                DataQueue<O> dataQueue) {
        this.config = config;
        this.formatter = formatter;
        this.filters = filters;
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
    public boolean consume(I data) {
        O format = formatter.format(data);
        for (DataFilter<O> filter : filters) {
            if (!filter.filter(format)) {
                return false;
            }
        }
        return dataQueue.put(format);
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
