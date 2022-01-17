package org.coastline.one.common.jvm.classloader;

/**
 * @author Jay.H.Zou
 * @date 2022/1/17
 */
public class ClassLoaderObject {

    public static void main(String[] args) {
        System.out.println("ONE IS ALL");
        System.out.println(ClassLoaderObject.class.getClassLoader());
    }
}
