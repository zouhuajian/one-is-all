package org.coastline.one.spring.config;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator;
import io.opentelemetry.context.propagation.ContextPropagators;
import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.SpanProcessor;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor;

/**
 * @author Jay.H.Zou
 * @date 2021/7/19
 */
public class OTelConfig {

    static  {
        // metricInit();
        // OpenTelemetrySdkAutoConfiguration.initialize();
    }

    private static final String ENDPOINT = "http://localhost:8001";

    private static final OtlpGrpcSpanExporter exporter = OtlpGrpcSpanExporter.builder().setEndpoint(ENDPOINT).build();

    private static final SpanProcessor spanProcessor = SimpleSpanProcessor.create(exporter);

    private static final Resource resource = Resource.create(Attributes.of(AttributeKey.stringKey("service.name"), "one-spring-demo"));
    private static final SdkTracerProvider sdkTracerProvider = SdkTracerProvider.builder()
            .setResource(resource)
            .addSpanProcessor(spanProcessor)
            .addSpanProcessor(BatchSpanProcessor.builder(OtlpGrpcSpanExporter.builder().build()).build())
            .build();

    private static final OpenTelemetry openTelemetry = OpenTelemetrySdk.builder()
            .setTracerProvider(sdkTracerProvider)
            .setPropagators(ContextPropagators.create(W3CTraceContextPropagator.getInstance()))
            .buildAndRegisterGlobal();

    public static OpenTelemetry getOpenTelemetry() {
        return openTelemetry;
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
