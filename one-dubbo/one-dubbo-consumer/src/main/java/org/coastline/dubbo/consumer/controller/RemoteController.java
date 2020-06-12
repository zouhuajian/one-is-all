package org.coastline.dubbo.consumer.controller;

import org.apache.dubbo.config.annotation.DubboReference;
import org.coastline.dubbo.api.entity.Subject;
import org.coastline.dubbo.api.service.ISubjectService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jay.H.Zou
 * @date 6/10/2020
 */
@RestController
public class RemoteController {

    /**
     * 如果 provider 填写版本，此处必须填写版本号，否则 JVM 无法启动
     */
    @DubboReference(version = "${one.service.version}")
    private ISubjectService subjectService;

    @RequestMapping("/query/{name}")
    @ResponseBody
    public String query(@PathVariable String name){
        Subject subject = subjectService.querySubject(name);
        return subject.toString();
    }

}
