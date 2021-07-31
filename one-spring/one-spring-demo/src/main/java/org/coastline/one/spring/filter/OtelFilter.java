package org.coastline.one.spring.filter;

import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.metrics.DoubleValueRecorder;
import io.opentelemetry.api.metrics.Meter;
import io.opentelemetry.api.metrics.common.Labels;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import org.coastline.one.spring.config.OTelConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;

/**
 * @author Jay.H.Zou
 * @date 2021/7/19
 */
public class OtelFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(OtelFilter.class);

    private static final String SPAN_NAME_URL = "monitor_provider";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String method = request.getMethod();
        String requestURI = request.getRequestURI();
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        int status = response.getStatus();
        logger.info("method = {}, url = {}, code = {}", method, requestURI, status);
        Tracer tracer = OTelConfig.getTracer();
        Span span = tracer.spanBuilder(SPAN_NAME_URL).setSpanKind(SpanKind.PRODUCER).startSpan();

        try (Scope scope = span.makeCurrent()) {
            // your use case
            Attributes attributes = Attributes.of(AttributeKey.stringKey("http_method"), method,
                    AttributeKey.stringKey("http_url"), requestURI,
                    AttributeKey.stringKey("http_code"), String.valueOf(status));
            span.setAllAttributes(attributes);
            span.addEvent("test_event");
            span.addEvent("have_attr", Attributes.of(AttributeKey.stringKey("event_attr_key"), "event_attr_value"));
            span.setStatus(StatusCode.OK, "this is ok");
            recordMetrics(span, request);
        } catch (Exception t) {
            span.setStatus(StatusCode.ERROR, t.getMessage());
        } finally {
            span.end(); // closing the scope does not end the span, this has to be done manually
        }
        /* tracer.spanBuilder("childWithLink")
                        .addLink(span.getSpanContext())
                        .startSpan().end(); */


        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void recordMetrics(Span span, HttpServletRequest request) {
        Random random = new Random();
        Meter meter = OTelConfig.getMeter();
        // 统计请求个数
        /*LongCounter counter = meter.longCounterBuilder("http_count")
                .setDescription("http request count")
                .setUnit("1").build();
        counter.add(1, Labels.of("http_url", request.getRequestURI()));
        // 队列长度
        LongUpDownCounter longUpDownCounter = meter.longUpDownCounterBuilder("test_up_down_counter")
                .build();
        longUpDownCounter.add(random.nextInt(50));*/
        // record duration
        DoubleValueRecorder duration = meter.doubleValueRecorderBuilder("duration")
                .setUnit("ms")
                .setDescription("duration metrics")
                .build();
        int i = random.nextInt(200);
        //i = i > 3 ? 1 : 5;
        duration.record(i,  Labels.of("http_url", request.getRequestURI()));
        System.out.println(i);
        // Build observer e.g. LongSumObserver
        /*meter.longSumObserverBuilder("cpu_usage")
                .setDescription("CPU Usage")
                .setUnit("%")
                .setUpdater(result -> {
                    // compute
                    result.observe(random.nextLong(), Labels.of("system", "cpu"));
                }).build();
        meter.doubleValueObserverBuilder("cpppu")
                .setUnit("%")
                .setUpdater(doubleResult -> doubleResult.observe((Math.random()), Labels.of("trace", "pu")))
                .build();*/


    }
}
