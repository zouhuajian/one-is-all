package org.coastline.one.spring.filter;

import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.metrics.*;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;
import org.coastline.one.spring.config.OTelConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * @author Jay.H.Zou
 * @date 2021/7/19
 */
public class OtelFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(OtelFilter.class);

    private static final String SPAN_NAME_URL = "monitor_provider";
    private LongCounter httpCounter;
    private DoubleHistogram httpDuration;
    private Tracer tracer;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        tracer = OTelConfig.getTracer();
        initMeter();
    }

    private void initMeter() {
        Meter meter = OTelConfig.getMeter();
        httpCounter = meter.counterBuilder("http_request_count")
                .setUnit("1")
                .build();
        // record duration
        httpDuration = meter.histogramBuilder("http_duration")
                .setUnit("ms")
                .setDescription("duration metrics")
                .build();
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
        //logger.info("method = {}, url = {}, code = {}", method, requestURI, status);
        tracer = OTelConfig.getTracer2();
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
            childOne(span);
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

    void childOne(Span parentSpan) throws InterruptedException {
        Span childSpan = tracer.spanBuilder("child")
                .setParent(Context.current().with(parentSpan))
                .startSpan();
        // do stuff
        TimeUnit.MILLISECONDS.sleep(2);
        childSpan.end();
    }

    private void recordMetrics(Span span, HttpServletRequest request) {
        Random random = new Random();
        // sum
        httpCounter.add(1);
        // histogram
        httpDuration.record(random.nextInt(200),
                Attributes.of(AttributeKey.stringKey("AttributeKey_http_url"), request.getRequestURI(),
                        AttributeKey.stringKey("AttributeKey_http_method"), request.getMethod()));
    }
}
