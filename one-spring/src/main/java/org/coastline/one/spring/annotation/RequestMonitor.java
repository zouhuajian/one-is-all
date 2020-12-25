package org.coastline.one.spring.annotation;

import java.lang.annotation.*;

/**
 * @author Jay.H.Zou
 * @date 2020/12/25
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.METHOD})
public @interface RequestMonitor {

    String value() default "";

}
