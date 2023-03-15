package org.coastline.one.spring;

import org.coastline.one.core.annotation.TimerHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author Jay.H.Zou
 * @date 2020/11/13
 */
@SpringBootApplication
public class OneSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(OneSpringApplication.class, args);
    }

    // Spring AOP
    //@Bean
    public TimerHandler initTimerHandler() {
        return new TimerHandler();
    }
}
