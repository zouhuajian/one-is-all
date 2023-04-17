package org.coastline.one.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jay.H.Zou
 * @date 2023/1/16
 */
public class OneTest {
    public static void main(String[] args) {
        // ListMultimap<String, String> map = ArrayListMultimap.create();
        Map<String, String> map = new HashMap<>();
        map.put("A", "1");
        map.put("A", "2");
        map.put("B", "1");
        map.forEach((k, v) -> {
            System.out.println(k + "_" + v);
        });
        String javaVersion = System.getProperty("java.version");
        System.out.println("Java version is " + javaVersion);
    }
}
