package com.edu.eduservice.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CourseQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    //一级分类id
    private String subjectParentId;

    //二级分类id
    private String subjectId;

    //课程名称
    private String title;

    //讲师id
    private String teacherId;


}
