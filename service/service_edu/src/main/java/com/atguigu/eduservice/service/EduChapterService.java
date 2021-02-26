package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author db
 * @since 2021-01-26
 */
public interface EduChapterService extends IService<EduChapter> {
    /**
     * 列表显示
     * @param courseId courseId
     * @return list
     */
    List<ChapterVo> getChapterVideoByCourseId(String courseId);
    /**
     * 删除章节，章节id
     * @param chapterId chapterId
     * @return boolean
     */
    boolean deleteChapter(String chapterId);
    /**
     * 根据课程id 删除对应的章节
     * @param courseId 根据课程id course_id
     */
    void removeChaterByCourseId(String courseId);
}
