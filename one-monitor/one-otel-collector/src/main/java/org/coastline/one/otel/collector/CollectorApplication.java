package org.coastline.one.otel.collector;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
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

    public static void main(String[] args) {
        loadConfig();
    }

    /**
     * local server config
     */
    private static void loadConfig() {
        Yaml yaml = new Yaml();
        // 文件路径是相对类目录(src/main/java)的相对路径
        ClassLoader classLoader = CollectorApplication.class.getClassLoader();
        if (classLoader.getResource(DEFAULT_CONFIG_NAME) == null) {
            logger.info("no application.yaml");
            return;
        }
        InputStream in = classLoader.getResourceAsStream(DEFAULT_CONFIG_NAME);
        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(yaml.loadAs(in, Map.class));
        logger.info("start config: {}", jsonElement);
        // TODO: build CollectorConfig

    }
}
