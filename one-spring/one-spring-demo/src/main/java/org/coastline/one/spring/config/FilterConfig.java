package org.coastline.one.spring.config;

import org.coastline.one.spring.filter.OtelFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jay.H.Zou
 * @date 2021/7/19
 */
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean otelFilter() {
        FilterRegistrationBean<OtelFilter> registration = new FilterRegistrationBean<>();
        OtelFilter filter = new OtelFilter();
        registration.setFilter(filter);
        registration.addUrlPatterns("/*");
        registration.setName("otel-filter");
        registration.setOrder(1);
        return registration;
    }
}
