package org.coastline.one.collector.config;

/**
 * data source receiver config
 *
 * @author Jay.H.Zou
 * @date 2021/7/20
 */
public class ReceiverConfig {
    public static final int DEFAULT_PORT_TRACE = 4317;

    public static final int DEFAULT_PORT_METRICS = 4318;

    public static final int DEFAULT_PORT_LOG = 4319;

    private int port;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
