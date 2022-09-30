package org.coastline.one.flink.common.config;

import org.apache.flink.configuration.ConfigOption;
import org.apache.flink.configuration.ConfigOptions;

import java.io.Serializable;
import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2021/8/23
 */
public class ConfigurationTool implements Serializable {
    private static final long serialVersionUID = 1L;

    private ConfigurationTool() {
    }

    /********************************* flink *********************************/
    public static final ConfigOption<String> JOB_NAME = ConfigOptions.key("flink.job-name").stringType().defaultValue("default_job_name");

    /********************************* kafka *********************************/
    public static final ConfigOption<String> KAFKA_BROKERS = ConfigOptions.key("kafka.brokers").stringType().noDefaultValue();
    public static final ConfigOption<String> KAFKA_TOPIC = ConfigOptions.key("kafka.topic").stringType().noDefaultValue();
    public static final ConfigOption<List<String>> KAFKA_TOPICS = ConfigOptions.key("kafka.topics").stringType().asList().noDefaultValue();
    public static final ConfigOption<String> KAFKA_GROUP_ID = ConfigOptions.key("kafka.group-id").stringType().noDefaultValue();

    /********************************* cos *********************************/
    public static final ConfigOption<String> COS_BUCKET_NAME = ConfigOptions.key("cos.bucket-name").stringType().noDefaultValue();
    public static final ConfigOption<String> COS_SECRET_ID = ConfigOptions.key("cos.secret-id").stringType().noDefaultValue();
    public static final ConfigOption<String> COS_SECRET_KEY = ConfigOptions.key("cos.secret-key").stringType().noDefaultValue();
    public static final ConfigOption<String> COS_REGION = ConfigOptions.key("cos.region").stringType().noDefaultValue();
    public static final ConfigOption<Integer> COS_QUEUE_SIZE = ConfigOptions.key("cos.queue-size").intType().defaultValue(1000);
    public static final ConfigOption<Integer> COS_BATCH_SIZE = ConfigOptions.key("cos.batch-size").intType().defaultValue(10);

    /********************************* hbase *********************************/
    public static final ConfigOption<String> HBASE_ZOOKEEPER_QUORUM = ConfigOptions.key("hbase.zookeeper.quorum").stringType().defaultValue("127.0.0.1");
    public static final ConfigOption<String> HBASE_TABLE = ConfigOptions.key("hbase.table").stringType().noDefaultValue();

    public static final ConfigOption<Integer> HBASE_ZOOKEEPER_PORT = ConfigOptions.key("hbase.zookeeper.port").intType().defaultValue(2181);

    /********************************* hbase *********************************/
    public static final ConfigOption<String> DATABASE_JDBC_URL = ConfigOptions.key("database.jdbc-url").stringType().noDefaultValue();
    public static final ConfigOption<String> DATABASE_USERNAME = ConfigOptions.key("database.username").stringType().noDefaultValue();
    public static final ConfigOption<String> DATABASE_PASSWORD = ConfigOptions.key("database.password").stringType().noDefaultValue();
    public static final ConfigOption<String> DATABASE_POOL_NAME = ConfigOptions.key("database.pool-name").stringType().defaultValue("flink-pool");
    public static final ConfigOption<String> DATABASE_MINIMUM_IDLE = ConfigOptions.key("database.minimum-idle").stringType().defaultValue("1");
    public static final ConfigOption<String> DATABASE_MAXIMUM_POOL_SIZE = ConfigOptions.key("database.maximum-pool-size").stringType().defaultValue("1");
}
