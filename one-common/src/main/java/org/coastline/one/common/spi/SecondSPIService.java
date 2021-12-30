package org.coastline.one.common.spi;

/**
 * @author Jay.H.Zou
 * @date 2021/12/27
 */
public class SecondSPIService implements SPIService{
    @Override
    public void execute() {
        System.out.println("second");
    }
}
