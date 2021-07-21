package org.coastline.one.otel.collector.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jay.H.Zou
 * @date 2021/7/21
 */
@RestController
@RequestMapping("/health")
public class HealthCheckController {

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

}
