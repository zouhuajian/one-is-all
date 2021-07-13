package org.coastline.one.spring.controller;

import io.opentelemetry.api.common.Labels;
import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.api.metrics.Meter;
import io.opentelemetry.sdk.metrics.MeterSdkProvider;
import org.coastline.one.spring.annotation.RequestMonitor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 * @author Jay.H.Zou
 * @date 2020/11/13
 */
@RestController
public class FirstController {

    @RequestMonitor("my_api")
    @GetMapping("/hello")
    public String hello(@RequestParam("key") String key) {
        metrics();
        return key;
    }

    @RequestMapping("/one/error")
    public Object error() {
        return 1 / 0;
    }

    private void metrics() {
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
