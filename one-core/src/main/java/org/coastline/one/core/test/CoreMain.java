package org.coastline.one.core.test;

import org.coastline.one.core.test.service.ComputeService;
import org.coastline.one.core.test.service.IComputeService;

/**
 * @author Jay.H.Zou
 * @date 2022/9/27
 */
public class CoreMain {
    public static void main(String[] args) {
        IComputeService computeService = new ComputeService();
        computeService.compute();
    }
}
