package org.coastline.one.common.proxy.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author Jay.H.Zou
 * @date 2022/1/29
 */
public class MoneyInterceptor implements MethodInterceptor {

    /**
     *
     * @param obj 表示要进行增强的对象
     * @param method 表示拦截的方法
     * @param args 数组表示参数列表，基本数据类型需要传入其包装类型，如int-->Integer、long-Long、double-->Double
     * @param proxy 表示对方法的代理，invokeSuper方法表示对被代理对象方法的调用
     * @return 执行结果
     * @throws Throwable 异常
     */
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.err.println("Before invoke " + method.getName());
        // 注意这里是调用invokeSuper而不是invoke，否则死循环;
        // proxy.invokeSuper 执行的是原始类的方法;
        // method.invoke 执行的是子类的方法;
        Object result = proxy.invokeSuper(obj, args);
        System.err.println("After invoke " + method.getName());
        return result;
    }
}
