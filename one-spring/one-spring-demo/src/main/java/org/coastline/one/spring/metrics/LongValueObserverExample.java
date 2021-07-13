package org.coastline.one.spring.metrics;

import io.opentelemetry.api.common.Labels;
import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.api.metrics.Meter;
import io.opentelemetry.sdk.metrics.MeterSdkProvider;

/**
 * @author Jay.H.Zou
 * @date 2021/7/13
 */
public class LongValueObserverExample {
    public static void main(String[] args) {
        MeterSdkProvider meterProvider = MeterSdkProvider.builder().build();
        Meter meter = meterProvider.get("instrumentation-library-name", "1.0.0");

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
