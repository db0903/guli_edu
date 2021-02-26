package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.subject.AllSubject;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author db
 * @since 2021-01-25
 */
public interface EduSubjectService extends IService<EduSubject> {

    /**
     * 添加课程分类
     * @param file file
     * @param subjectService subjectService
     */
    void saveSubject(MultipartFile file,EduSubjectService subjectService);

    /**
     * 2级分类
     * @return
     */
    List<OneSubject> getAllOneTwoSubject();

}
