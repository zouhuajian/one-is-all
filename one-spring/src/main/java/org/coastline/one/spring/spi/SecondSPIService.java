package org.coastline.one.spring.spi;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * @author Jay.H.Zou
 * @date 2021/12/27
 */
@Order(Ordered.HIGHEST_PRECEDENCE + 19)
public class SecondSPIService implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        System.out.println("second");
    }
}