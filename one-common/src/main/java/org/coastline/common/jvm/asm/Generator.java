package org.coastline.common.jvm.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @author Jay.H.Zou
 * @date 2020/12/24
 */
public class Generator {

    public static void main(String[] args) throws Exception {
        //读取
        ClassReader classReader = new ClassReader("org/coastline/common/jvm/asm/BaseProcess");
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        //处理
        ClassVisitor classVisitor = new MyClassVisitor(classWriter);
        classReader.accept(classVisitor, ClassReader.SKIP_DEBUG);
        byte[] data = classWriter.toByteArray();
        //输出
        File f = new File("one-common/target/classes/org/coastline/common/jvm/asm/BaseProcess.class");
        FileOutputStream fout = new FileOutputStream(f);
        fout.write(data);
        fout.close();
        System.out.println("now generator cc success!!!!!");
    }
}
