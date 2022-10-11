package org.coastline.one.core.test.service;

import org.coastline.one.core.annotation.Timer;

/**
 * @author Jay.H.Zou
 * @date 2022/9/27
 */
public interface IComputeService {

    @Timer(name = "interface_compute")
    void compute();
}
