package org.coastline.one.otel.collector.config;

import org.coastline.one.otel.collector.model.DataSourceType;

/**
 * @author Jay.H.Zou
 * @date 2021/7/20
 */
public class ReceiverConfig {

    public static final int DEFAULT_PORT_TRACE = 4317;

    public static final int DEFAULT_PORT_METRICS = 4318;

    public static final int DEFAULT_PORT_LOG = 4319;

    private DataSourceType dataSourceType;

    private int port;

    public DataSourceType getDataSourceType() {
        return dataSourceType;
    }

    public void setDataSourceType(DataSourceType dataSourceType) {
        this.dataSourceType = dataSourceType;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
