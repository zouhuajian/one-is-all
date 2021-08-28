package org.coastline.one.spring.annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Jay.H.Zou
 * @date 2020/12/25
 */
//@Aspect
//@Component
public class RequestInterceptor {

    public RequestInterceptor() {
        System.out.println("check start...");
    }

    @Pointcut("@annotation(org.coastline.one.spring.annotation.RequestMonitor)")
    private void pointcut() {
    }


    @Before("pointcut() && @annotation(requestMonitor)")
    public void advice(JoinPoint joinPoint, RequestMonitor requestMonitor) {
        System.out.println("=========================");
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        HttpSession session = request.getSession();
        if (session == null) {
            return;
        }

        System.out.println(joinPoint.getArgs());
        System.out.println("--- Kth日志的内容为[" + requestMonitor.value() + "] ---");
    }

    @AfterReturning(pointcut = "pointcut() && @annotation(requestMonitor)", returning = "returnResult")
    public void afterReturning(JoinPoint joinPoint, RequestMonitor requestMonitor) {
        System.out.println("=========================");
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        HttpSession session = request.getSession();

        System.out.println(joinPoint.getArgs());
        System.out.println("--- Kth日志的内容为[" + requestMonitor.value() + "] ---");
    }
}
