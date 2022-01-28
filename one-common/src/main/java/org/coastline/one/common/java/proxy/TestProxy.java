package org.coastline.one.common.java.proxy;


import java.lang.reflect.Proxy;

/**
 * @author Jay.H.Zou
 * @date 2022/1/27
 */
public class TestProxy {
    public static void main(String[] args) {
        //生成$Proxy0的class文件
        //System.getProperties().setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        IMoneyService moneyService = new MoneyService();
        System.out.println("origin: " + moneyService.getClass());

        ProxyHandler handler = new ProxyHandler(moneyService);
        IMoneyService proxyMoneyService = (IMoneyService) Proxy.newProxyInstance(moneyService.getClass().getClassLoader(),
                moneyService.getClass().getInterfaces(), handler);

        System.out.println("proxy: " + proxyMoneyService.getClass());
        System.out.println(proxyMoneyService.haveMoney());
        System.out.println(proxyMoneyService.haveMoney());
    }
}
