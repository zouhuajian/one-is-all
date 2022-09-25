package org.coastline.one.spring.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.Proxy;
import org.springframework.context.ApplicationContext;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Jay.H.Zou
 * @date 2022/9/23
 */
/*public class BeanProcessor implements BeanPostProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(BeanProcessor.class);

    private final List<ProxyEntity> proxyEntities;
    private final ApplicationContext context;

    public BeanProcessor(List<ProxyEntity> proxyEntities, ApplicationContext context) {
        this.proxyEntities = proxyEntities;
        this.context = context;
    }

    *//**
     * process bean
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     *//*
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        try {
            Class<?> clz = bean.getClass();
            for (ProxyEntity entity : this.proxyEntities) {
                if (clz.isAnnotationPresent(entity.getAnnotation())) {
                    bean = initBean(bean, entity, clz);
                    continue;
                }

                Class<?> c = superClass(clz.getSuperclass(), entity.getAnnotation());
                if (c != null) {
                    bean = initBean(bean, entity, c);
                    continue;
                }
                c = implementClass(clz.getInterfaces(), entity.getAnnotation());
                if (c != null) {
                    bean = initBean(bean, entity, c);
                }
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return bean;
    }

    private Object initBean(Object bean, ProxyEntity entity, Class<?> iface) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        try {
            Class<?>[] classes = entity.getArg() == null ?
                    new Class<?>[]{Object.class, iface.getAnnotation(entity.getAnnotation()).annotationType()} :
                    getClass(iface, entity);
            Constructor<? extends InvocationHandler> constructor = entity.getClz().getConstructor(classes);
            Object[] objects = entity.getArg() == null ? new Object[]{bean, iface.getAnnotation(entity.getAnnotation())} : getBean(bean, iface, entity);
            return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{iface}, constructor.newInstance(objects));
        } catch (NoSuchMethodException e) {
            return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{iface}, entity.getClz().newInstance());
        }
    }

    private Object[] getBean(Object bean, Class<?> iface, ProxyEntity entity) {
        Object[] objects = new Object[entity.getArg().length + 2];
        objects[0] = bean;
        objects[1] = iface.getAnnotation(entity.getAnnotation());
        int i = 2;
        for (Class<?> clz : entity.getArg()) {
            objects[i] = this.context.getBean(clz);
            i++;
        }
        return objects;
    }

    private Class<?>[] getClass(Class<?> iface, ProxyEntity entity) {
        Class<?>[] classes = new Class<?>[entity.getArg().length + 2];
        classes[0] = Object.class;
        classes[1] = iface.getAnnotation(entity.getAnnotation()).annotationType();
        int i = 2;
        for (Class<?> c : entity.getArg()) {
            classes[i] = c;
            ++i;
        }
        return classes;
    }

    private Class<?> implementClass(Class<?>[] interfaces, Class<? extends Annotation> annotation) {
        if (interfaces == null || interfaces.length < 1) {
            return null;
        }
        for (Class<?> clz : interfaces) {
            if (clz.isAnnotationPresent(annotation)) {
                return clz;
            }
            Class<?> c = superClass(clz.getSuperclass(), annotation);
            if (c != null) {
                return c;
            }
        }
        return null;
    }

    private Class<?> superClass(Class<?> clz, Class<? extends Annotation> annotation) {
        if (clz == null || "java.lang.Object".equals(clz.getName())) {
            return null;
        }
        if (clz.isAnnotationPresent(annotation)) {
            return clz;
        }
        return superClass(clz.getSuperclass(), annotation);
    }
}*/
