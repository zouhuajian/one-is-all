package org.coastline.one.common.aspectj.test.service;

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
