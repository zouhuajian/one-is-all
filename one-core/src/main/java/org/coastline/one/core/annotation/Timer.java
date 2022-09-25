package org.coastline.one.core.annotation;

import java.lang.annotation.*;

/**
 * @author Jay.H.Zou
 * @date 2022/9/23
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Timer {
    String name();
}
