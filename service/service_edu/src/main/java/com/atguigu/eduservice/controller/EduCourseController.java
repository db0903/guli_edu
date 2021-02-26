package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.Result;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 * @author db
 * @since 2021-01-26
 */
@Api("课程")
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;
    /**
     * 课程列表接口，条件查询分页展示,后台编写分页
     * @param current current
     * @param limit limit
     * @param coursePublishVo coursePublishVo
     * @return Result
     */
    @ApiOperation(value = "分页课程列表")
    @PostMapping ("getListCourseSql/{current}/{limit}")
    public Result getListCourseSql(
            @PathVariable Long current,
            @PathVariable Long limit,
            @RequestBody(required = false) CoursePublishVo coursePublishVo){
        Page<CoursePublishVo> coursePage = new Page<>(current,limit);
        List<CoursePublishVo> list = eduCourseService.pageQuerySql(coursePage, coursePublishVo);
        long total = coursePage.getTotal();
        return Result.ok().data("total",total).data("rows",list);
}


    /**
     * 添加课程基本信息
     * @param courseInfoVo courseInfoVo
     * @return Result
     */
    @ApiOperation(value = "添加课程基本信息addCourseInfo")
    @PostMapping("addCourseInfo")
    public Result addCourseInfo(
            @ApiParam(name = "courseInfoVo", value = "添加课程基本信息", required = true)
            @RequestBody CourseInfoVo courseInfoVo){
        String id = eduCourseService.saveCourseInfo(courseInfoVo);
        return Result.ok().data("courseId", id);
    }
    /**
     * 根据课程id查询基本信息
     * @param courseId courseId
     * @return Result
     */
    @ApiOperation(value = "课程id查询基本信息getCourseInfo")
    @GetMapping("getCourseInfo/{courseId}")
    public Result getCourseInfo(
            @ApiParam(name = "courseId", value = "课程id", required = true)
            @PathVariable String courseId){
        CourseInfoVo courseInfoVo = eduCourseService.getCourseInfo(courseId);
        return Result.ok().data("courseInfoVo",courseInfoVo);
    }
    /**
     * 修改基本信息
     * @param courseInfoVo courseInfoVo
     * @return Result
     */
    @ApiOperation(value = "课程id修改基本信息updateCourseInfo")
    @PostMapping("updateCourseInfo")
    public Result updateCourseInfo(
            @ApiParam(name = "courseId", value = "课程id修改基本信息", required = true)
            @RequestBody  CourseInfoVo courseInfoVo){
        eduCourseService.updateCourseInfo(courseInfoVo);
        return Result.ok();
    }
    /**
     * 根据课程id显示需要的课程信息
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "课程id显示课程信息")
    @GetMapping("getPublishCourseInfo/{id}")
    public Result getPublishCourseInfo(@PathVariable String id){
        CoursePublishVo publishCourse = eduCourseService.publishCourseInfo(id);
        return Result.ok().data("publishCourse",publishCourse);
    }
    /**
     * 课程发布，修改状态
     * @param id id
     * @return Result
     */
    @PostMapping("publishCourse/{id}")
    public Result publishCourse(@PathVariable String id){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        //课程发布状态
        eduCourse.setStatus("Normal");
        boolean flag = eduCourseService.updateById(eduCourse);
        return flag ? Result.ok() : Result.error();
    }
    /**
     * 如果用户确定删除，则首先删除video记录，然后删除chapter记录，最后删除Course记录
     * @param id 课程id
     * @return 结果
     */
    @ApiOperation(value = "根据ID删除课程")
    @DeleteMapping("{id}")
    public Result deleteById(@PathVariable String id){
        eduCourseService.removeCourse(id);
        return Result.ok();
    }
}

