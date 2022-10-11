package org.coastline.one.core.test.service;

import org.coastline.one.core.annotation.Timer;

/**
 * @author Jay.H.Zou
 * @date 2022/9/27
 */
public class ComputeService implements IComputeService {

    //@Timer(name = "class_compute")
    @Override
    public void compute() {
        System.out.println("1+1=2");
    }
}
