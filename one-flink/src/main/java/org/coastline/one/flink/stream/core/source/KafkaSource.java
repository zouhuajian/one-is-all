package org.coastline.one.flink.stream.core.source;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.coastline.one.flink.common.util.ConfigurationTool;

import java.io.Serializable;
import java.util.List;
import java.util.Properties;

/**
 * kafka source factory
 * @author Jay.H.Zou
 * @date 2021/8/19
 */
public class KafkaSource implements Serializable {
    private static final long serialVersionUID = 1L;

    public static FlinkKafkaConsumer<byte[]> create(Configuration configuration) {

        // 构建kafka连接配置
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, configuration.getString(ConfigurationTool.KAFKA_BROKERS));

        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, configuration.getString(ConfigurationTool.KAFKA_GROUP_ID));
        /*properties.setProperty("session.timeout.ms", "600000");
        properties.setProperty("heartbeat.interval.ms", "300000");
        // 等待多久后处理消息
        properties.setProperty("fetch.max.wait.ms", "60000");
        properties.setProperty("request.timeout.ms", "600000");
        // 指定consumer两次poll的最大时间间隔（默认5分钟）
        properties.setProperty("max.poll.interval.ms", "600000");*/
        List<String> topics = configuration.get(ConfigurationTool.KAFKA_TOPICS);
        return create(properties, topics);
    }

    public static FlinkKafkaConsumer<byte[]> create(Properties properties, List<String> topics) {
        MonitorKafkaDeserializationSchema kafkaSource = new MonitorKafkaDeserializationSchema();
        return new FlinkKafkaConsumer<>(topics, kafkaSource, properties);
    }




}
