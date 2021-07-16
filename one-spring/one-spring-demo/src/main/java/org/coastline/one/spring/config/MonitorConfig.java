package org.coastline.one.spring.config;

import io.opentelemetry.sdk.autoconfigure.OpenTelemetrySdkAutoConfiguration;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @author Jay.H.Zou
 * @date 2021/7/16
 */
@Component
public class MonitorConfig implements InitializingBean {


    @Override
    public void afterPropertiesSet() throws Exception {
        // metricInit();
        OpenTelemetrySdkAutoConfiguration.initialize();
    }

    private static void metricInit(){
        /*MetricExporter metricExporter =
                OtlpGrpcMetricExporter.builder()
                        .setChannel(ManagedChannelBuilder.forAddress("opentelemetry_collector", 4318).usePlaintext().build())
                        .build();
        SdkMeterProvider.builder().buildAndRegisterGlobal();
        IntervalMetricReader.builder()
                .setMetricProducers(Collections.singleton((SdkMeterProvider) GlobalMeterProvider.get()))
                .setExportIntervalMillis(1000)// configurable interval
                .setMetricExporter(metricExporter)
                .build().start();
        meter = GlobalMeterProvider.get().get("com.ddmc.opentelemetryspringdemo.DemoController");*/

    }

}
