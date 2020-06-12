package org.coastline.dubbo.api.service;

import org.coastline.dubbo.api.entity.Subject;

/**
 * @author Jay.H.Zou
 * @date 6/9/2020
 */
public interface ISubjectService {

    Subject querySubject(String subjectName);

}
