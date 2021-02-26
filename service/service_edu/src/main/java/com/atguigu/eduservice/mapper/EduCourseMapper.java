package com.atguigu.eduservice.mapper;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.lettuce.core.dynamic.annotation.Param;
import java.util.List;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author db
 * @since 2021-01-26
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    /**
     * 前端页面显示的课程的需要信息
     * @param courseId courseId
     * @return CoursePublishVo
     */
    public CoursePublishVo getPublishCourseInfo(@Param("courseId") String courseId);

    /**
     * 前端页面List显示
     * @param coursePage coursePage
     * @param coursePublishVo CoursePublishVo
     * @return List
     */
    public List<CoursePublishVo> selectCoursetList( Page<CoursePublishVo> coursePage,  CoursePublishVo coursePublishVo);
}
