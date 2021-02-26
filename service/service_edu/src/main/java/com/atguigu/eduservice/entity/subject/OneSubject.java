package com.atguigu.eduservice.entity.subject;

import lombok.Data;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author db
 * @date 2021/1/26 - 13:32
 * 一级分类
 */
@Data
public class OneSubject implements Serializable {

    private String id;
    private String title;
    private List<TwoSubject> children = new ArrayList<>();
}
