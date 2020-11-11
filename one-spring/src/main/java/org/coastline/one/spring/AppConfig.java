package org.coastline.one.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jay.H.Zou
 * @date 2020/11/10
 */
@Configuration
@ComponentScan(basePackageClasses = {org.coastline.one.spring.service.IItemService.class})
public class AppConfig {
}
