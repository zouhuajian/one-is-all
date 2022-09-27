package org.coastline.one.spring.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.coastline.one.spring.annotation.RequestMonitor;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author Jay.H.Zou
 * @date 2020/12/25
 */
@Aspect
//@Component
public class RequestInterceptor {

    @Pointcut("@annotation(org.coastline.one.spring.annotation.RequestMonitor)")
    private void pointcut() {
    }

    @Before("pointcut() && @annotation(requestMonitor)")
    public void advice(JoinPoint joinPoint, RequestMonitor requestMonitor) {
        System.out.println("=========================");
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();

        HttpSession session = request.getSession();
        if (session == null) {
            return;
        }
        System.out.println(Arrays.toString(joinPoint.getArgs()));
        System.out.println("--- Kth日志的内容为[" + requestMonitor.value() + "] ---");
    }

}
