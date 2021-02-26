package com.atguigu.eduservice.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * @author db
 * @date 2021/2/2 - 16:20
 */
@ApiModel(value = "课程发布信息")
@Data
public class CoursePublishVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherId;
    private String teacherName;
    private String price;
    private String gmtModified;
    private String begin;
    private String end;

}
