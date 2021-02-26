package com.atguigu.eduservice.controller;

import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.atguigu.commonutils.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-01-15
 */
@Api("讲师管理")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;
    /**
     * 查询所有数据
     * @return Result
     */
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public Result findAllTeacher() {
        List<EduTeacher> list = eduTeacherService.list(null);
        return Result.ok().data("items", list);
    }
    /**
     * 条件查询分页 RequestBody 提交方式为post
     * @param current current
     * @param limit limit
     * @param teacherQuery teacherQuery
     * @return Result
     */
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public Result pageTeacherCondition(@ApiParam(name = "current", value = "当前页", required = true) @PathVariable Long current,
                                       @ApiParam(name = "limit", value = "显示数量", required = true) @PathVariable Long limit,
                                       @RequestBody(required = false) TeacherQuery teacherQuery){
        //创建page对象
        Page<EduTeacher> teacherPage = new Page<>(current,limit);
        //构建条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        //多条件组合查修  mybatis 动态sql
        //姓名
        String name = teacherQuery.getName();
        //讲师头衔
        Integer level = teacherQuery.getLevel();
        //开始时间
        String begin = teacherQuery.getBegin();
        //结束时间
        String end = teacherQuery.getEnd();
        String gmtCreate = "gmt_create";
        //判断条件是否为空
        if(!StringUtils.isEmpty(name)){
            //构建条件 数据库的表字段
            wrapper.like("name", name);
        }
        if(!StringUtils.isEmpty(level)){
            //构建条件 数据库的表字段
            wrapper.eq("level", level);
        }
        if(!StringUtils.isEmpty(begin)){
            //构建条件 数据库的表字段 ge开始时间
            wrapper.ge(gmtCreate, begin);
        }
        if(!StringUtils.isEmpty(end)){
            //构建条件 数据库的表字段 le 结束时间
            wrapper.le(gmtCreate, end);
        }
        //排序，创建时间降序排序
        wrapper.orderByDesc(gmtCreate);
        //调用方法实现分页
        IPage<EduTeacher> page = eduTeacherService.page(teacherPage, wrapper);
        //总记录数
        long total = teacherPage.getTotal();
        //数据的list集合
        List<EduTeacher> records = teacherPage.getRecords();
        return Result.ok().data("total",total).data("rows",records);
    }
    /**
     * 添加讲师的方法
     * @param teacher teacher
     * @return Result
     */
    @ApiOperation(value = "讲师对象EduTeacher添加")
    @PostMapping("addTeacher")
    public Result addTeacher(@ApiParam(name = "eduTeacher", value = "讲师对象EduTeacher", required = true) @RequestBody EduTeacher teacher){
        boolean flag = eduTeacherService.save(teacher);
        return flag ? Result.ok() : Result.error();
    }
    /**
     * 讲师修改功能
     * @param teacher teacher
     * @return Result
     */
    @ApiOperation(value = "根据ID修改讲师")
    @PostMapping("updteTeacher")
    public Result updteTeacher(@ApiParam(name = "EduTeacher", value = "讲师对象", required = true) @RequestBody EduTeacher teacher){
        boolean flag = eduTeacherService.updateById(teacher);
        return flag ? Result.ok() : Result.error();
    }
    /**
     * 根据讲师ID进行查询
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping("getTeacher/{id}")
    public Result getTeacher(@ApiParam(name = "id", value = "讲师ID", required = true) @PathVariable String id){
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return Result.ok().data("teacher",eduTeacher);
    }
    /**
     * 逻辑删除讲师的方法，单个删除
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("{id}")
    public Result removeById(@ApiParam(name = "id", value = "讲师ID", required = true) @PathVariable String id) {
        boolean flag = eduTeacherService.removeById(id);
        return flag ? Result.ok() : Result.error();
    }
    /**
     * 逻辑删除讲师的方法，批量删除
     * @param ids ids
     * @return Result
     */
    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("ids")
    public Result removeByIds( @RequestParam(value = "ids", required = false) List<String> ids) {
        boolean flag = eduTeacherService.removeByIds(ids);
        return flag ? Result.ok() : Result.error();
    }
}


