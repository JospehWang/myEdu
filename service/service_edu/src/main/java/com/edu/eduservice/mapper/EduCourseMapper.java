package com.edu.eduservice.mapper;

import com.edu.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edu.eduservice.entity.frontvo.CourseWebVo;
import com.edu.eduservice.entity.vo.CoursePublishVo;
import org.mapstruct.Mapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author yufan
 * @since 2022-08-29
 */
@Mapper
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    public CoursePublishVo getPublishCourseInfo(String courseId);
    //根据课程id，编写sql语句查询课程信息
    CourseWebVo getBaseCourseInfo(String courseId);
}
