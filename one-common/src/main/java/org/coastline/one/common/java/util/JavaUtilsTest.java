package org.coastline.one.common.java.util;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Jay.H.Zou
 * @date 2022/1/23
 */
public class JavaUtilsTest {
    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();
    private static final  ThreadLocal<String> threadLocal2 = new ThreadLocal<>();
    private static final ThreadLocal<String> threadLocal3 = new ThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("a", "12");
        Map<String, String> stringStringMap = Collections.synchronizedMap(hashMap);

        Hashtable<String, String> hashtable = new Hashtable<>();
        hashtable.put("a", "12");
        Enumeration<String> elements = hashtable.elements();

        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("a", "1");
        linkedHashMap.get("a");

        IdentityHashMap<String, String> identityHashMap = new IdentityHashMap<>();


        // queue

        // vector
        Vector<String> vector = new Vector<>();

        // string
        StringBuilder stringBuilder = new StringBuilder();

        StringBuffer stringBuffer = new StringBuffer();
        String a = "a";
        // thread local
        threadLocal.set(a);
        threadLocal2.set("b");
        threadLocal3.set("c");
        System.out.println(Thread.currentThread());
        System.out.println(threadLocal.get());
        System.out.println(Thread.currentThread());
        System.out.println(threadLocal2.get());
        Thread.sleep(1000);
        // random
        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
    }
}
