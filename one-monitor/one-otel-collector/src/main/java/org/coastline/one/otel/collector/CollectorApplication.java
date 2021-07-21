package org.coastline.one.otel.collector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * server start
 *
 * @author Jay.H.Zou
 * @date 2021/7/20
 */
@SpringBootApplication
public class CollectorApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(CollectorApplication.class, args);
    }

}
