package org.coastline.one.spring.common;

import org.coastline.one.core.annotation.Timer;
import org.coastline.one.core.annotation.TimerHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * @author Jay.H.Zou
 * @date 2022/9/23
 */
@Component
public class MyBeanProcessor implements BeanPostProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyBeanProcessor.class);

    @Bean
    public TimerHandler initTimerHandler() {
        return new TimerHandler();
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();
        processClazz(beanName, clazz);
        return bean;
    }


    private void processClazz(String beanName, Class<?> clazz) {
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
    }
}
