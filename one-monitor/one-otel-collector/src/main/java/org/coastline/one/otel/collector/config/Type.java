package org.coastline.one.otel.collector.config;

/**
 * @author Jay.H.Zou
 * @date 2021/7/23
 */
public enum Type {

    RECEIVERS("receivers"),

    PROCESSORS("processors"),

    EXPORTERS("exporters");

    private String value;

    Type(String value) {
        this.value = value;
    }
}
