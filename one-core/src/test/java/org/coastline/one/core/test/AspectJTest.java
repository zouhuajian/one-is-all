package org.coastline.one.core.test;

import org.coastline.one.core.test.aop.TestService;
import org.junit.Test;

/**
 * @author Jay.H.Zou
 * @date 2022/9/24
 */
public class AspectJTest {

    @Test
    public  void testAspectJ() {
        TestService testService = new TestService();
        testService.compute();
    }
}
