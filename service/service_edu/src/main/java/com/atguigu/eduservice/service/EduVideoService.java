package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author db
 * @since 2021-01-26
 */
public interface EduVideoService extends IService<EduVideo> {
    /**
     * 删除操作
     * @param courseId 课程id
     */
    void removeVideoByCourseId(String courseId);
}
