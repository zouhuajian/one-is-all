package org.coastline.one.otel.collector.receiver;

import org.coastline.one.otel.collector.config.ReceiverConfig;

import java.io.IOException;

/**
 * 数据接收端
 *
 * @author Jay.H.Zou
 * @date 2021/7/20
 */
public interface DataReceiver {

    void start(ReceiverConfig config) throws IOException;

    void shutdown() throws InterruptedException;

}
