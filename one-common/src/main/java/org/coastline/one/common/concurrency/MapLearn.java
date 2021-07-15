package org.coastline.one.common.concurrency;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Jay.H.Zou
 * @date 2020/6/19
 */
public class MapLearn {

    public static void main(String[] args) {
        ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>(16);
        concurrentHashMap.put("one", "one");

    }

}
