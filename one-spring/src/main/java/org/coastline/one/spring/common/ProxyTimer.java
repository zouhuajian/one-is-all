package org.coastline.one.spring.common;

import org.coastline.one.core.annotation.Timer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Jay.H.Zou
 * @date 2022/9/28
 */
public class ProxyTimer implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Timer timer = method.getAnnotation(Timer.class);
        if (timer != null) {

        }
        return null;
    }
}
