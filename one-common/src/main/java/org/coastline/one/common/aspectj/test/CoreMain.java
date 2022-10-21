package org.coastline.one.common.aspectj.test;

import org.coastline.one.common.aspectj.test.service.ComputeService;
import org.coastline.one.common.aspectj.test.service.IComputeService;

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
