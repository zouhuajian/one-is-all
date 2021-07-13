package org.coastline.one.spring.metrics;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.common.Labels;
import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.api.metrics.Meter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.autoconfigure.OpenTelemetrySdkAutoConfiguration;
import io.opentelemetry.sdk.metrics.MeterSdkProvider;
import io.opentelemetry.sdk.resources.Resource;

import static io.opentelemetry.api.common.AttributeKey.stringKey;

/**
 * @author Jay.H.Zou
 * @date 2021/7/13
 */
public class LongValueObserverExample {
    public static void main(String[] args) {
        Attributes attributes = Attributes.of(stringKey("otel.resource.attributes"), "resource_value",
                stringKey("resource_key"), "resource_value",
                stringKey("resource_key"), "resource_value");
        Resource resource = Resource.create(attributes);
        OpenTelemetry.builder().setMeterProvider(MeterSdkProvider.builder().setResource(resource).build());
        Meter meter = OpenTelemetry.getGlobalMeter("instrumentation-library-name","semver:1.0.0");


        OpenTelemetrySdk sdk = OpenTelemetrySdkAutoConfiguration.initialize();
        MeterSdkProvider meterProvider = MeterSdkProvider.builder().build();

        // Build counter e.g. LongCounter
        LongCounter counter = meter
                .longCounterBuilder("processed_jobs")
                .setDescription("Processed jobs")
                .setUnit("1")
                .build();

        // It is recommended that the API user keep a reference to a Bound Counter for the entire time or
        // call unbind when no-longer needed.
        LongCounter.BoundLongCounter someWorkCounter = counter.bind(Labels.of("Key", "SomeWork"));

        // Record data
        someWorkCounter.add(123);

        // Alternatively, the user can use the unbounded counter and explicitly
        // specify the labels set at call-time:
        counter.add(123, Labels.of("Key", "SomeWork"));
    }
}
