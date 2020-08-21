package org.coastline.one.sentinel.window;

/**
 * @author Jay.H.Zou
 * @date 2020/8/16
 */
public class SlidingWindow {

    public static void main(String[] args) throws InterruptedException {
        int windowLength = 500;
        int arrayLength = 2;
        calculate(windowLength, arrayLength);

        Thread.sleep(100);
        calculate(windowLength, arrayLength);

        Thread.sleep(200);
        calculate(windowLength, arrayLength);

        Thread.sleep(200);
        calculate(windowLength, arrayLength);

        for (int i = 0; i < 5; i++) {
            Thread.sleep(500);
            calculate(windowLength, arrayLength);
        }

    }

    private static void calculate(int windowLength, int arrayLength) {
        long time = System.currentTimeMillis();
        long timeId = time / windowLength;
        long currentWindowStart = time - time % windowLength;
        int idx = (int) (timeId % arrayLength);
        System.out.println("time = " + time + ", currentWindowStart = " + currentWindowStart + ", timeId = " + timeId + ", idx = " + idx);
    }

}
