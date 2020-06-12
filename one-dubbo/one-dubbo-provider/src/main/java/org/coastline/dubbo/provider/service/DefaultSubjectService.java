package org.coastline.dubbo.provider.service;

import org.apache.dubbo.config.annotation.DubboService;
import org.coastline.dubbo.api.entity.Subject;
import org.coastline.dubbo.api.service.ISubjectService;

/**
 * @author Jay.H.Zou
 * @date 6/9/2020
 */
@DubboService(version = "${one.service.version}")
public class DefaultSubjectService implements ISubjectService {

    @Override
    public Subject querySubject(String subjectName) {
        System.out.println(subjectName);
        return new Subject(subjectName + " " + Math.random(), 24 * 60 * 60 * 1000L);
    }
}
