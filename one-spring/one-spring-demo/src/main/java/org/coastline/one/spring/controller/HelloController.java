package org.coastline.one.spring.controller;

import org.coastline.one.spring.annotation.RequestMonitor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 * @author Jay.H.Zou
 * @date 2020/11/13
 */
@RestController
public class HelloController {

    @RequestMonitor("")
    @GetMapping("/hello")
    public String hello(@RequestParam("key") String key) {
        return key;
    }

    @RequestMapping("/one/error")
    public Object error() {
        return 1 / 0;
    }

}
