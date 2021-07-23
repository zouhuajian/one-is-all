package org.coastline.one.otel.collector.config;

import org.coastline.one.otel.collector.config.component.ComponentID;

import java.util.Map;

/**
 * @author Jay.H.Zou
 * @date 2021/7/23
 */
public class CollectorConfig {

    // EXTENSIONS_KEY_NAME is the configuration key name for extensions section.
    private static String EXTENSIONS_KEY_NAME = "extensions";

    // RECEIVERS_KEY_NAME is the configuration key name for receivers section.
    private static String RECEIVERS_KEY_NAME = "receivers";

    // EXPORTERS_KEY_NAME is the configuration key name for exporters section.
    private static String EXPORTERS_KEY_NAME ="exporters";

    // processorsKeyName is the configuration key name for processors section.
    private static String PROCESSORS_KEY_NAME = "processors";

    // PIPELINES_KEY_NAME is the configuration key name for pipelines section.
    private static String PIPELINES_KEY_NAME = "pipelines";





    /*public class ConfigSettings {
        private Map<String, Map<String, JsonObject>> receivers;

        private Map<String, Map<String, JsonObject>> processors;

        private Map<String, Map<String, JsonObject>> exporters;

        private Map<String, Map<String, JsonObject>> extensions;

        private Map<String, Map<String, JsonObject>> service;
    }

    public class ServiceSettings {
        private List<String> extensions;

        private Map<String, PipelineSettings> pipelines;

    }

    public class PipelineSettings {
        private List<String> receivers;

        private List<String> processors;

        private List<String> exporters;
    }*/

}
