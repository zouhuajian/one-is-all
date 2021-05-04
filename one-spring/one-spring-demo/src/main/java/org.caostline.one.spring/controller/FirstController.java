package org.caostline.one.spring.controller;

import org.caostline.one.spring.annotation.RequestMonitor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * @author Jay.H.Zou
 * @date 2020/11/13
 */
@RestController
public class FirstController {

    @RequestMonitor("firsttttt")
    @RequestMapping("/first")
    public String first() {
        return "first controller";
    }

    @RequestMapping("/doError")
    public Object error() {
        return 1 / 0;
    }

}
