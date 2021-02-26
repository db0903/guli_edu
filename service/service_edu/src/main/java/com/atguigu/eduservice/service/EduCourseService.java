package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 * @author db
 * @since 2021-01-26
 */
public interface EduCourseService extends IService<EduCourse> {
    /**
     * 注解sql
     * @param coursePage coursePage
     * @param coursePublishVo coursePublishVo
     */
    List<CoursePublishVo> pageQuerySql(Page<CoursePublishVo> coursePage, CoursePublishVo coursePublishVo);
    /**
     * 添加课程的接口
     * @param courseInfoVo courseInfoVo
     * @return String
     */
    String saveCourseInfo(CourseInfoVo courseInfoVo);
    /**
     * 根据课程id查询基本信息
     * @param courseId courseId
     * @return CourseInfoVo
     */
    CourseInfoVo getCourseInfo(String courseId);
    /**
     * 修改基本信息
     * @param courseInfoVo courseInfoVo
     */
    void updateCourseInfo(CourseInfoVo courseInfoVo);
    /**
     * 根据课程,多表联查显示需要的信息
     * @param id id
     * @return CoursePublishVo
     */
    CoursePublishVo publishCourseInfo(String id);
    /**
     * 删除课程
     * @param id 课程id
     */
    void removeCourse(String id);


}
