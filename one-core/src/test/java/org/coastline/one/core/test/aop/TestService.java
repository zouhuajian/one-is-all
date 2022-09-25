package org.coastline.one.core.test.aop;

import org.coastline.one.core.annotation.Timer;

import java.util.concurrent.TimeUnit;

/**
 * @author Jay.H.Zou
 * @date 2022/9/23
 */
public class TestService implements ITestService {

    @Timer(name = "child")
    @Override
    public void compute() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
        }
        System.out.println("compute");
    }
}
