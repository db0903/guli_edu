package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-01-15
 */
public interface EduTeacherService extends IService<EduTeacher> {
    /**
     * 分页返回所有的数据
     * @param pageTeacher 分页数据
     * @return map包含所有
     */
    Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageTeacher);
}
