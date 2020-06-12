package org.coastline.dubbo.api.entity;

import java.io.Serializable;

/**
 * Need Serializable
 *
 * @author Jay.H.Zou
 * @date 6/9/2020
 */
public class Subject implements Serializable {

    private String subjectName;

    private Long duration;

    public Subject() {
    }

    public Subject(String subjectName, Long duration) {
        this.subjectName = subjectName;
        this.duration = duration;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Subject={" +
                "subjectName='" + subjectName + '\'' +
                ", duration=" + duration +
                '}';
    }
}
