package org.coastline.one.core.annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.coastline.one.core.tool.TimeTool;

/**
 * @author Jay.H.Zou
 * @date 2022/9/23
 */
@Aspect
public class TimerHandler {

    @Around("@annotation(org.coastline.one.core.annotation.Timer)")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        MethodSignature methodSignature = ((MethodSignature) joinPoint.getSignature());
        Timer timer = methodSignature.getMethod().getAnnotation(Timer.class);
        String name = timer.name();
        long startTime = TimeTool.currentTimeMillis();
        result = joinPoint.proceed();
        String message = name + " cost time = " + (TimeTool.currentTimeMillis() - startTime) + "ms.";
        System.err.println(message);
        return result;
    }
}
