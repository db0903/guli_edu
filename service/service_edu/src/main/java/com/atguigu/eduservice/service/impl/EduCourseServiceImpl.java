package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.CourseQuery;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 * @author db
 * @since 2021-01-26
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    @Autowired
    private EduCourseDescriptionService courseDescriptionService;
    @Autowired
    private EduChapterService eduChapterService ;
    @Autowired
    private EduVideoService eduVideoService;
    /**
     * sql 实现
     * @param coursePage coursePage
     * @param coursePublishVo courseQuery
     */
    @Override
    public List<CoursePublishVo> pageQuerySql(Page<CoursePublishVo> coursePage, CoursePublishVo coursePublishVo) {
        List<CoursePublishVo> list = null;
        list =  baseMapper.selectCoursetList(coursePage,coursePublishVo);
        return list;
    }
    /**
     * @param courseInfoVo courseInfoVo
     * @return String
     */
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {  //添加到课程的方法
        //课程表中添加信息  edu_course
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        //this.save(eduCourse); 相同
        int insert = baseMapper.insert(eduCourse);
        //添加失败
        if(0 == insert){
            throw new GuliException(20001,"添加课程信息失败");
        }
        //课程简介表中添加信息 //获取 edu_course 的id
        String cid = eduCourse.getId();
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setId(cid);
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescriptionService.save(courseDescription);
        return cid;
    }
    /**
     * @param courseId courseId
     * @return CourseInfoVo
     * 根据课程id查询基本信息
     */
    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        //查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse, courseInfoVo);
        //查询描述表
        EduCourseDescription courseDescription = courseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());
        return courseInfoVo;
    }
    /**
     * 修改基本信息
     * @param courseInfoVo courseInfoVo
     */
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        //修改课程表 edu_course
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if(0 == update){
            throw new GuliException(20001, "修改课程信息失败");
        }
        //修改描述表 edu_course_description
        EduCourseDescription description = new EduCourseDescription();
        description.setId(courseInfoVo.getId());
        description.setDescription(courseInfoVo.getDescription());
        boolean flag = courseDescriptionService.updateById(description);
        if(!flag){
            throw new GuliException(20001, "修改课程描述信息失败");
        }
    }
    /**
     * 根据课程,多表联查显示需要的信息
     * @param id id
     * @return CoursePublishVo
     */
    @Override
    public CoursePublishVo publishCourseInfo(String id) {
        CoursePublishVo publishCourseInfo = null;
        publishCourseInfo = baseMapper.getPublishCourseInfo(id);
        return publishCourseInfo;
    }
    /**
     *如果用户确定删除，则首先删除video记录，然后删除chapter记录，最后删除Course记录
     * @param courseId 课程id
     */
    @Override
    public void removeCourse(@PathVariable String courseId) {
        //删除小节
        eduVideoService.removeVideoByCourseId(courseId);
        //根据课程id删除章节
        eduChapterService.removeChaterByCourseId(courseId);
        //根据课程id删除描述
        courseDescriptionService.removeById(courseId);
        //根据课程id删除课程本身
        int result = baseMapper.deleteById(courseId);
        if(result == 0){
            throw new GuliException(20001, "课程删除失败");
        }
    }

}
