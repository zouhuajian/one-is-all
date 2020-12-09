package org.coastline.one.spring.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * @author Jay.H.Zou
 * @date 2020/11/13
 */
@RestController
public class FirstController {

    @RequestMapping("/first")
    public String first() {
        return "first controller";
    }

    @RequestMapping("/doError")
    public Object error() {
        return 1 / 0;
    }

}
