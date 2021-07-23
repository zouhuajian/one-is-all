package org.coastline.one.otel.collector.server;

import com.google.gson.Gson;
import org.coastline.one.otel.collector.component.impl.DefaultComponentFactory;
import org.coastline.one.otel.collector.config.OldCollectorConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * grp
 *
 * @author Jay.H.Zou
 * @date 2021/7/21
 */
@Component
public class CollectorServer implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(CollectorServer.class);

    private static final String DEFAULT_CONFIG_NAME = "application.yaml";

    @Autowired
    private OldCollectorConfig config;

    @Override
    public void afterPropertiesSet() throws Exception {
        Gson gson = new Gson();
        logger.info("trace receiver config: {}", gson.toJsonTree(config.getTraceReceiverConfig()));
        logger.info("trace export config: {}", gson.toJsonTree(config.getTraceExportersConfig()));
        logger.info("metrics receiver config: {}", gson.toJsonTree(config.getMetricsReceiverConfig()));
        logger.info("metrics export config: {}", gson.toJsonTree(config.getTraceExportersConfig()));

        startServers(config);
    }

    public void startServers(OldCollectorConfig config) throws Exception {
        DefaultComponentFactory.createTraceComponents(config);
        DefaultComponentFactory.createMetricsComponents(config);
    }


    /**
     * local server config
     */
    /*private static CollectorConfig loadConfig() {
        Yaml yaml = new Yaml();
        // 文件路径是相对类目录(src/main/java)的相对路径
        ClassLoader classLoader = CollectorApplication.class.getClassLoader();
        if (classLoader.getResource(DEFAULT_CONFIG_NAME) == null) {
            logger.info("no application.yaml");
            throw new ConfigException();
        }
        InputStream in = classLoader.getResourceAsStream(DEFAULT_CONFIG_NAME);
        Gson gson = new Gson();
        JsonObject jsonObject = gson.toJsonTree(yaml.loadAs(in, Map.class)).getAsJsonObject();

        CollectorConfig config = new CollectorConfig();
        // logger.info("start config: {}", jsonElement);
        JsonObject receivers = jsonObject.getAsJsonObject("receivers");
        JsonObject traceReceiver = receivers.getAsJsonObject("trace");
        ReceiverConfig traceReceiverConfig = gson.fromJson(traceReceiver, ReceiverConfig.class);
        logger.info("trace receiver config = {}", traceReceiver.toString());
        ReceiverConfig metricsReceiverConfig = gson.fromJson(receivers.getAsJsonObject("metrics"), ReceiverConfig.class);
        config.setTraceReceiverConfig(traceReceiverConfig);
        config.setTraceDataQueue(new QueueConfig());
        return config;
    }*/
}
