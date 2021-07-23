package org.coastline.one.otel.collector.config;

/**
 * @author Jay.H.Zou
 * @date 2021/7/20
 */

public class OldExporterConfig {

    private String brokers;

    private String topic;

    public String getBrokers() {
        return brokers;
    }

    public void setBrokers(String brokers) {
        this.brokers = brokers;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

}
