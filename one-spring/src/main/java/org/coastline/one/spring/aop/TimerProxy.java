package org.coastline.one.spring.aop;

import org.coastline.one.core.annotation.Timer;
import org.coastline.one.core.tool.TimeTool;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Jay.H.Zou
 * @date 2022/9/28
 */
public class TimerProxy implements InvocationHandler {

    private Object obj;

    public TimerProxy(Object obj) {
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Timer timer = method.getAnnotation(Timer.class);
        if (timer != null) {
            System.out.println("start to enhance method: " + method.getName());
            long startTime = TimeTool.currentTimeMillis();
            Object invoke = method.invoke(obj, args);
            String message = timer.name() + " cost time = " + (TimeTool.currentTimeMillis() - startTime) + "ms.";
            System.out.println(message);
            return invoke;
        }
        return method.invoke(obj, args);
    }
}
