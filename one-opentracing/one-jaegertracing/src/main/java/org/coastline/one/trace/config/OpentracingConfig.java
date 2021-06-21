package org.coastline.one.trace.config;

import io.jaegertracing.Configuration;
import io.jaegertracing.internal.samplers.ConstSampler;
import io.opentracing.util.GlobalTracer;

/**
 * @author Jay.H.Zou
 * @date 2021/6/21
 */
public class OpentracingConfig {

    public static void main(String[] args) {
        Configuration.SamplerConfiguration samplerConfig = Configuration.SamplerConfiguration.fromEnv()
                .withType(ConstSampler.TYPE)
                .withParam(1);

        Configuration.ReporterConfiguration reporterConfig = Configuration.ReporterConfiguration.fromEnv()
                .withLogSpans(true);

        Configuration config = new Configuration("helloWorld")
                .withSampler(samplerConfig)
                .withReporter(reporterConfig);

        GlobalTracer.registerIfAbsent(config.getTracer());
    }

}
