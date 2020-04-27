package org.coastline.algorithm.gc;

/**
 * @author Jay.H.Zou
 * @date 2020/2/18
 */
public class FinalizeEscapeGC {

    private static FinalizeEscapeGC SAVE_HOOK = null;

    public void isAlive() {
        System.out.println("Yes, I'm still alive, :)");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("Finalize method executed!");
        FinalizeEscapeGC.SAVE_HOOK = this;
    }

    public static void main(String[] args) throws InterruptedException {
        SAVE_HOOK = new FinalizeEscapeGC();

        // 对象第一次成功拯救自己
        SAVE_HOOK = null;
        System.gc();

        // 因为 finalize 方法优先级很低，在此等待 0.5 秒
        Thread.sleep(500);

        if (SAVE_HOOK != null) {
            SAVE_HOOK.isAlive();
        } else {
            System.out.println("No, I'm dead, :(");
        }
        // 以下的代码同上，但是它被回收了
        SAVE_HOOK = null;
        System.gc();

        // 因为 finalize 方法优先级很低，在此等待 0.5 秒
        Thread.sleep(500);

        if (SAVE_HOOK != null) {
            SAVE_HOOK.isAlive();
        } else {
            System.out.println("No, I'm dead, :(");
        }

    }

}
