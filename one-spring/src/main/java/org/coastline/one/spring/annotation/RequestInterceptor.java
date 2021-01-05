package org.coastline.one.spring.annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author Jay.H.Zou
 * @date 2020/12/25
 */
@Aspect
@Component
public class RequestInterceptor {

    public RequestInterceptor() {
        System.out.println("check start...");
    }

    @Pointcut("@annotation(org.coastline.one.spring.annotation.RequestMonitor)")
    private void pointcut(){}


    @Before("pointcut() && @annotation(requestMonitor)")
    public void advice(JoinPoint joinPoint, RequestMonitor requestMonitor) {
        System.out.println("=========================");
        System.out.println(joinPoint.getArgs());
        System.out.println("--- Kth日志的内容为[" + requestMonitor.value() + "] ---");
    }
}
