package com.atguigu.eduservice.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author db
 * @date 2021/1/26 - 15:54
 */
@Data
public class AllSubject {
    /**
     * id
     * 课程类别ID
     */
    private String id;
    /**
     * title
     * 类别名称
     */
    private String title;
    /**
     * children
     * 一个一级分类有多个二级分类
     */
    private List<AllSubject> children = new ArrayList<>();
}
