package org.coastline.one.common.java.classloader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Jay.H.Zou
 * @date 2022/1/17
 */
public class CoastlineClassLoader extends ClassLoader {

    public CoastlineClassLoader(ClassLoader parent) {
        super(parent);
    }

    public CoastlineClassLoader() {
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            String cname = "E:\\project\\zouhuajian\\one-is-all\\one-common\\src\\main\\java\\org\\coastline\\one\\common\\jvm\\classloader\\ClassLoaderObject.txt";
            byte[] classBytes = Files.readAllBytes(Paths.get(cname));
            Class<?> cl = defineClass(name, classBytes, 0, classBytes.length);
            if (cl == null) {
                throw new ClassNotFoundException(name);
            }
            System.out.println("custom class loader...");
            return cl;
        } catch (IOException e) {
            e.printStackTrace();
            throw new ClassNotFoundException(name);
        }
    }
}
