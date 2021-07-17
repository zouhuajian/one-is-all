package org.coastline.one.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Add opentelemetry(2021/07/15)
 *
 * @author Jay.H.Zou
 * @date 2020/11/13
 */
@EnableTransactionManagement
@SpringBootApplication
public class OneSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(OneSpringApplication.class, args);
    }

}
