package com.edu.eduservice.service;

import com.edu.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author yufan
 * @since 2022-08-29
 */
public interface EduVideoService extends IService<EduVideo> {
    //1 根据课程id删除小节
    void removeVideoByCourseId(String courseId);
}
