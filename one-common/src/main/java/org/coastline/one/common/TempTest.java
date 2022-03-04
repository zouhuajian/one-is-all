package org.coastline.one.common;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author Jay.H.Zou
 * @date 2022/2/25
 */
public class TempTest {

    public static void main(String[] args) {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        queue.poll();
    }
}
