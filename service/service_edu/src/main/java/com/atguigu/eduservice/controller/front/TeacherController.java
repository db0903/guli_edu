package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.Result;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * @author db
 * @date 2021/3/3 - 10:54
 */
@Api("客户端讲师显示")
@RestController
@RequestMapping("/eduservice/teacherfront")
@CrossOrigin
public class TeacherController {
    @Autowired
    private EduTeacherService eduTeacherService;
    @Autowired
    private EduCourseService eduCourseService;
    /**
     * 前端讲师页面分页显示
     * @param page 页数
     * @param limit 单页显示的数量
     * @return Result结果
     */
    @GetMapping("getTeacherFrontList/{page}/{limit}")
    public Result getTeacherFrontList(@PathVariable Long page, @PathVariable Long limit){
        Page<EduTeacher> pageTeacher = new Page<>(page,limit);
        Map<String,Object> map = eduTeacherService.getTeacherFrontList(pageTeacher);
        //返回分页中的所有数据
        return Result.ok().data(map);
    }
    @GetMapping("getTeacherFrontInfo/{teacherId}")
    public Result getTeacherFrontInfo(@PathVariable String teacherId){
        //根据讲师id 查询讲师级基本信息
        EduTeacher teacherById = eduTeacherService.getById(teacherId);
        //根据讲师id 查询课程信息
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id", teacherId);
        List<EduCourse> courseList = eduCourseService.list(wrapper);
        return Result.ok().data("teacher",teacherById).data("courseList",courseList);
    }
}
