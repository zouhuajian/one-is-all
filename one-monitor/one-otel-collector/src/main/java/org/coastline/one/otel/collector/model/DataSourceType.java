package org.coastline.one.otel.collector.model;

/**
 * @author Jay.H.Zou
 * @date 2021/7/20
 */
public enum DataSourceType {

    TRACE("trace"),

    METRICS("metrics"),

    LOG("log");

    private String name;

    DataSourceType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
