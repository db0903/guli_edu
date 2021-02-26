package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.Result;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author db
 * @since 2021-01-25
 */
@Api("课程科目")
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;
    /**
     * 获取上传的文件，读取文件内容
     * @param file  file
     * @return Result
     */
    @PostMapping("addSubject")
    public Result addSubject(@ApiParam(name = "file", value = "读取Excel", required = true) MultipartFile file){
        //上传过来excel 文件
        subjectService.saveSubject(file,subjectService);
        return Result.ok();
    }

    /**
     * 课程分类列表2级分类
     * @return  Result
     */
    @GetMapping("getAllSubject")
    public Result getAllSubject(){
        //list集合泛型是一级分类
        List<OneSubject> list = subjectService.getAllOneTwoSubject();
        return Result.ok().data("list",list);
    }
}

