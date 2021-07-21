package org.coastline.one.otel.collector;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.coastline.one.otel.collector.component.impl.DefaultComponentFactory;
import org.coastline.one.otel.collector.config.CollectorConfig;
import org.coastline.one.otel.collector.config.QueueConfig;
import org.coastline.one.otel.collector.config.ReceiverConfig;
import org.coastline.one.otel.collector.exception.ConfigException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * server start
 *
 * @author Jay.H.Zou
 * @date 2021/7/20
 */
public class CollectorApplication {

    private static final Logger logger = LoggerFactory.getLogger(CollectorApplication.class);

    private static final String DEFAULT_CONFIG_NAME = "application.yaml";

    public static void main(String[] args) throws Exception {
        loadBanner();
        CollectorConfig config = loadConfig();
        DefaultComponentFactory.createTraceComponents(config);
        DefaultComponentFactory.createMetricsComponents(config);
    }

    /**
     * local server config
     */
    private static CollectorConfig loadConfig() {
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
        config.setQueueConfig(new QueueConfig());
        return config;
    }

    private static void loadBanner() {
        String banner = "banner.txt";
        ClassLoader classLoader = CollectorApplication.class.getClassLoader();
        if (classLoader.getResource(banner) == null) {
            return;
        }
        InputStream in = classLoader.getResourceAsStream(banner);
        try {
            String result = CharStreams.toString(new InputStreamReader(in, Charsets.UTF_8));
            logger.info(result);
        } catch (Exception ignored) {
        }
    }

}
