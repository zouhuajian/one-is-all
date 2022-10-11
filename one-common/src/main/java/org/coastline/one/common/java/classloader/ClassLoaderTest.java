package org.coastline.one.common.java.classloader;

import java.lang.reflect.Method;

/**
 * @author Jay.H.Zou
 * @date 2022/1/16
 */
public class ClassLoaderTest {

    public static void main(String[] args) throws Exception {
        System.out.println(ClassLoader.getSystemClassLoader());
        System.out.println(ClassLoader.getSystemClassLoader().getParent());
        ClassLoader parent = ClassLoader.getSystemClassLoader().getParent();
        System.out.println(parent.getParent());

        // 个人的经验来看，最容易出问题的点是第二行的打印出来的是"sun.misc.Launcher$AppClassLoader"。造成这个问题的关键在于IDEA预编译了
        //1、删除CLASSPATH下的 ClassLoaderObject.class，CLASSPATH下没有 ClassLoaderObject.class，Application ClassLoader就把这个.class文件交给下一级用户自定义ClassLoader去加载了
        //2、如下， 即把自定义ClassLoader的父加载器设置为Extension ClassLoader，这样父加载器加载不到Person.class，就交由子加载器MyClassLoader来加载了
        CoastlineClassLoader coastlineClassLoader = new CoastlineClassLoader(ClassLoader.getSystemClassLoader().getParent());
        Class<?> clazz = coastlineClassLoader.loadClass("org.coastline.one.common.java.classloader.ClassLoaderObject");

        //利用反射获取main方法
        Method method = clazz.getDeclaredMethod("main", String[].class);
        Object object = clazz.newInstance();
        String[] arg = new String[0];
        method.invoke(object, (Object) arg);
        System.out.println("自定义类加载器: " + clazz.getClassLoader());
        System.out.println("默认类加载器: " + ClassLoaderObject.class.getClassLoader());
    }
}
