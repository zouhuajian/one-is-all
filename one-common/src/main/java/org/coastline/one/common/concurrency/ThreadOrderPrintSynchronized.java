package org.coastline.one.common.concurrency;

/**
 * @author Jay.H.Zou
 * @date 2020/9/24
 */
public class ThreadOrderPrintSynchronized {

    public static void main(String[] args) throws Exception {
        Object a = new Object();
        Object b = new Object();
        Object c = new Object();
        ThreadPrinter pa = new ThreadPrinter("A",c, a);
        ThreadPrinter pb = new ThreadPrinter("B",a, b);
        ThreadPrinter pc = new ThreadPrinter("C\n",b, c);

        Thread t1 = new Thread(pa);
        Thread t2 = new Thread(pb);
        Thread t3 = new Thread(pc);

        t1.start();
        Thread.sleep(10);
        t2.start();
        Thread.sleep(10);
        t3.start();
    }

    public static class ThreadPrinter implements Runnable {
        private final String name;
        private final Object prev;
        private final Object self;

        public ThreadPrinter(String name, Object prev, Object self) {
            this.name = name;
            this.prev = prev;
            this.self = self;
        }

        @Override
        public void run() {
            int count = 10;
            while (count > 0) {// 多线程并发，不能用if，必须使用 while 循环
                synchronized (prev) { // 先获取 prev 锁
                    synchronized (self) {// 再获取 self 锁
                        System.out.print(name);
                        count--;
                        self.notifyAll();// 唤醒其他线程竞争self锁，注意此时self锁并未立即释放。
                    }
                    // 此时执行完self的同步块，这时self锁才释放。
                    try {
                        if (count == 0) {// 如果count==0,表示这是最后一次打印操作，通过notifyAll操作释放对象锁。
                            prev.notifyAll();
                        } else {
                            prev.wait(); // 立即释放 prev锁，当前线程休眠，等待唤醒
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
