package spi;

import org.coastline.one.common.spi.SPIService;
import sun.misc.Service;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author Jay.H.Zou
 * @date 2021/12/27
 */
public class TestSPI {
    public static void main(String[] args) {
        Iterator<SPIService> providers = Service.providers(SPIService.class);
        while (providers.hasNext()) {
            SPIService ser = providers.next();
            ser.execute();
        }
        System.out.println("--------------------------------");

        ServiceLoader<SPIService> load = ServiceLoader.load(SPIService.class);
        for (SPIService ser : load) {
            ser.execute();
        }
    }
}
