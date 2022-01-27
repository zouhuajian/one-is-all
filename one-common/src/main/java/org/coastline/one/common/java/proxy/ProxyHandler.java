package org.coastline.one.common.java.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Jay.H.Zou
 * @date 2022/1/27
 */
public class ProxyHandler implements InvocationHandler {

    private Object target;

    public ProxyHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.err.println("Before invoke " + method.getName());
        method.invoke(target, args);
        System.err.println("After invoke " + method.getName());
        return null;
    }

}
