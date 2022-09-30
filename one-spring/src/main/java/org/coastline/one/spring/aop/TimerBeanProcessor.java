package org.coastline.one.spring.aop;

import org.coastline.one.core.annotation.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.LinkedList;

/**
 * @author Jay.H.Zou
 * @date 2022/9/23
 */
@Component
public class TimerBeanProcessor implements BeanPostProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(TimerBeanProcessor.class);

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        try {
            Class<?> clazz = bean.getClass();
            Class<?>[] interfaces = processClazz(clazz);
            if (interfaces != null && interfaces.length > 0) {
                TimerProxy proxy = new TimerProxy(bean);
                return Proxy.newProxyInstance(clazz.getClassLoader(), interfaces, proxy);
            }
        } catch (Exception e) {
            LOGGER.error("proxy for bean error.", e);
        }
        return bean;
    }

    /**
     * only for interfaces
     *
     * @param clazz
     */
    private Class<?>[] processClazz(Class<?> clazz) {
        LinkedList<Class<?>[]> queue = new LinkedList<>();
        queue.add(new Class<?>[]{clazz});
        while (!queue.isEmpty()) {
            Class<?>[] classes = queue.removeFirst();
            for (Class<?> clz : classes) {
                Class<?>[] interfaces = clz.getInterfaces();
                Method[] methods = ReflectionUtils.getAllDeclaredMethods(clz);
                for (Method method : methods) {
                    Timer annotation = AnnotationUtils.findAnnotation(method, Timer.class);
                    if (annotation == null) {
                        continue;
                    }
                    String name = annotation.name();
                    System.out.println(clazz.getName() + "." + method.getName() + ":" + name);
                    return interfaces;
                }
                queue.addLast(interfaces);
            }
        }
        return null;
    }


    /**
     * all class
     */
    /*private void processClazz(String beanName, Class<?> clazz) {
        if (clazz == null) {
            return;
        }
        Method[] methods = ReflectionUtils.getAllDeclaredMethods(clazz);
        for (Method method : methods) {
            Timer annotation = AnnotationUtils.findAnnotation(method, Timer.class);
            if (annotation == null) {
                continue;
            }
            String name = annotation.name();
            System.out.println(clazz.getName() + "." + method.getName() + ":" + name);
        }
        Class<?> superClazz = clazz.getSuperclass();
        Class<?>[] interfaces = clazz.getInterfaces();
        processClazz(beanName, superClazz);
        for (Class<?> interfaceClazz : interfaces) {
            processClazz(beanName, interfaceClazz);
        }
    }*/
}
