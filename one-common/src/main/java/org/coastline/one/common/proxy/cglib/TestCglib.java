package org.coastline.one.common.proxy.cglib;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;
import org.coastline.one.common.proxy.MoneyService;

/**
 * @author Jay.H.Zou
 * @date 2022/1/29
 */
public class TestCglib {

    public static void main(String[] args) {
        // 代理类class文件存入本地磁盘，可反编译查看源码
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "./");

        // 通过CGLIB动态代理获取代理对象的过程
        // 创建Enhancer对象，类似于JDK动态代理的Proxy类
        Enhancer enhancer = new Enhancer();
        // 设置目标类的字节码文件
        enhancer.setSuperclass(MoneyService.class);
        // 设置回调函数
        enhancer.setCallback(new MoneyInterceptor());
        // create 方法正式创建代理类
        MoneyService moneyService = (MoneyService) enhancer.create();
        // 调用代理类的具体业务方法
        moneyService.haveMoney();
    }
}
