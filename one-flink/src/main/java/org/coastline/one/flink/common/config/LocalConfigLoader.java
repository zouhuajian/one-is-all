package org.coastline.one.flink.common.config;

import com.google.common.collect.Maps;
import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.api.java.utils.ParameterTool;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Jay.H.Zou
 * @date 2021/8/6
 */
public class LocalConfigLoader implements ConfigLoader {

    private LocalConfigLoader(){}

    public static LocalConfigLoader create() {
        return new LocalConfigLoader();
    }

    @Override
    public ExecutionConfig.GlobalJobParameters load() {
        String path = "application.yaml";
        ClassLoader classLoader = ConfigLoader.class.getClassLoader();
        if (classLoader.getResource(path) == null) {
            return ParameterTool.fromMap(Maps.newHashMap());
        }
        try (InputStream in = classLoader.getResourceAsStream(path)) {
            return ParameterTool.fromPropertiesFile(in).getConfiguration();
        } catch (IOException e) {
            throw new RuntimeException("read config error", e);
        }
    }

}
