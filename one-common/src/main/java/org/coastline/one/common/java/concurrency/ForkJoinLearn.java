package org.coastline.one.common.java.concurrency;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @author Jay.H.Zou
 * @date 2020/6/25
 */
public class ForkJoinLearn {

    private static final Integer MAX = 4;

    public static void main(String[] args) {

        testForkJoin();
    }

    public static void testForkJoin2(){
        // 单例
        // ForkJoinPool pool = ForkJoinPool.commonPool();

    }

    public static void testForkJoin() {
        int[] data = {3, 5, 6, 8, 1, 0, 9, 7, 5, 8, 10, 23, 7, 8, 10};

        int sum = 0;
        for (int i = 0; i < data.length; i++) {
            sum += data[i];
        }
        System.out.println("data:" + sum);
        // 这是Fork/Join框架的线程池
        ForkJoinPool pool = new ForkJoinPool();

        int start = 0;
        int end = data.length - 1;

        ForkJoinTask<Integer> taskFuture = pool.submit(new MyForkJoinTask(start, end, data));
        try {

            Integer result = taskFuture.get();
            System.out.println("result = " + result);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }


    static class MyForkJoinTask extends RecursiveTask<Integer> {

        // 子任务开始计算的值
        private Integer startValue;

        // 子任务接数计算的值
        private Integer endValue;

        // 需要计算的内容
        private int[] data;

        public MyForkJoinTask(Integer startValue, Integer endValue, int[] data) {
            this.startValue = startValue;
            this.endValue = endValue;
            this.data = data;
        }

        @Override
        protected Integer compute() {
            // 如果条件成立，说明这个任务所需要计算的数值分为足够小了
            // 可以正式进行累加计算了
            boolean isCompute = (endValue - startValue) < MAX;

            if (isCompute) {

                Integer totalValue = 0;

                for (int i = this.startValue; i <= this.endValue; i++) {
                    totalValue += data[i];
                }

                System.out.println("线程：" + Thread.currentThread().getId()
                        + "-开始计算的部分：startValue = " + startValue +
                        "-endValue = " + endValue +
                        "- 间隔：" + (endValue - startValue));

                return totalValue;
            } else {
                // 否则再进行任务拆分，拆分成两个任务
                int middle = (startValue + endValue) / 2;

                MyForkJoinTask subTask1 = new MyForkJoinTask(startValue, middle, data);
                MyForkJoinTask subTask2 = new MyForkJoinTask(middle + 1, endValue, data);

                // 任务的批量提交, 执行拆分
                invokeAll(subTask1, subTask2);

                // 等待子任务执行完返回，合并其结果
                int sum = subTask1.join() + subTask2.join();
                return sum;
            }
        }
    }
}
