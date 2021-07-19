package org.coastline.one.spring.config.filter;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.trace.Span;
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

/**
 * @author Jay.H.Zou
 * @date 2021/7/19
 */
public class OTelFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(OTelFilter.class);

    private static final String SPAN_NAME_URL = "monitor_provider";
    private OpenTelemetry openTelemetry;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        openTelemetry = OTelConfig.getOpenTelemetry();
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
        Tracer tracer = openTelemetry.getTracer("otel-sdk", "1.4.1");
        Span span = tracer.spanBuilder(SPAN_NAME_URL).startSpan();

        try (Scope scope = span.makeCurrent()) {
            // your use case
            Attributes attributes = Attributes.of(AttributeKey.stringKey("http_method"), method,
                    AttributeKey.stringKey("http_url"), requestURI,
                    AttributeKey.stringKey("http_code"), String.valueOf(status));
            span.setAllAttributes(attributes);
            span.addEvent("test_event");
        } catch (Exception t) {
            span.setStatus(StatusCode.ERROR, t.getMessage());
        } finally {
            span.end(); // closing the scope does not end the span, this has to be done manually
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
