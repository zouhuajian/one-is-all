package org.coastline.one.core.config;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * @author Jay.H.Zou
 * @date 2023/3/21
 */
public class ConfigLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigLoader.class);

    private final Properties properties;

    public ConfigLoader(String path) throws IOException {
        Preconditions.checkNotNull(path, "Config file path can't be null");
        ClassLoader cL = Thread.currentThread().getContextClassLoader();
        URL resource = cL.getResource(path);
        if (resource == null) {
            throw new RuntimeException(path + " does not exist.");
        }
        LOGGER.info("Use config file {}", resource.getPath());
        InputStream resourceAsStream = cL.getResourceAsStream(resource.getPath());
        properties = new Properties();
        properties.load(resourceAsStream);
    }


    public String getConfig(String key) {
        return getConfig(key, false);
    }

    public String getConfigOrDefault(String key, String defaultValue) {
        return getConfigOrDefault(key, false, defaultValue);
    }

    public String getConfig(String key, boolean necessary) {
        String value = properties.getProperty(key);
        if (necessary && Strings.isNullOrEmpty(value)) {
            throw new RuntimeException(key + " is not configured.");
        }
        return value;
    }

    public String getConfigOrDefault(String key, boolean necessary, String defaultValue) {
        String value = properties.getProperty(key);
        if (necessary && Strings.isNullOrEmpty(value)) {
            throw new RuntimeException(key + " is not configured.");
        }
        if (Strings.isNullOrEmpty(value)) {
            return defaultValue;
        }
        return value;
    }

}
