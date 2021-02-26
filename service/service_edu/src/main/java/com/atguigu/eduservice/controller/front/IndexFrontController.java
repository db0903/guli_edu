package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.Result;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author db
 * @date 2021/2/18 - 10:08
 */
@Api("客户端课程显示")
@RestController
@RequestMapping("/eduservice/indexfront")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class IndexFrontController {
    @Autowired
    private EduTeacherService eduTeacherService;
    @Autowired
    private EduCourseService eduCourseService;
    /**
     *查询8条热门课程和前4条名师
     * @return  Result
     */
    @GetMapping("index")
    public Result index(){
        //查询8条热门课程
        QueryWrapper<EduCourse> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.orderByDesc("buy_count");
        courseQueryWrapper.last("limit 8");
        List<EduCourse> eduCourseList = eduCourseService.list(courseQueryWrapper);
        //查询前4个名师
        QueryWrapper<EduTeacher> teacherQueryWrapper = new QueryWrapper<>();
        teacherQueryWrapper.orderByAsc("sort");
        teacherQueryWrapper.last("limit 4");
        List<EduTeacher> eduTeacherList = eduTeacherService.list(teacherQueryWrapper);
        return Result.ok().data("eduCourseList",eduCourseList).data("eduTeacherList",eduTeacherList);
    }
}
