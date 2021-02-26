package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.Result;
import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.service.EduChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author db
 * @since 2021-01-26
 */
@Api("课程章节管理")
@RestController
@RequestMapping("/eduservice/chapter")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class EduChapterController {

    @Autowired
    private EduChapterService chapterService;

    /**
     * 根据courseId 查询章节小节
     * @param courseId
     * @return
     */
    @ApiOperation(value = "嵌套章节数据列表")
    @GetMapping("getChapterVideo/{courseId}")
    public Result getChapterVideo(
            @ApiParam(name = "courseId", value = "课程ID", required = true)
            @PathVariable String courseId){
        List<ChapterVo> allChapterVideo  = chapterService.getChapterVideoByCourseId(courseId);
        return Result.ok().data("allChapterVideo",allChapterVideo );
    }
    /**
     * 添加章节
     * @param chapter
     * @return
     */
    @ApiOperation(value = "添加章节")
    @PostMapping("addchapter")
    public Result addchapter(
            @ApiParam(name = "eduChapter", value = "添加EduChapter 对象", required = true)
            @RequestBody EduChapter chapter){
        chapterService.save(chapter);
        return Result.ok();
    }
    /**
     * 根据id 查询回显
     * @param chapterId
     * @return
     */
    @ApiOperation(value = "根据id查询回显")
    @GetMapping("getChapterInfo/{chapterId}")
    public Result query(
            @ApiParam(name = "chapterId", value = "章节ID", required = true)
            @PathVariable String chapterId){
        EduChapter chapter = chapterService.getById(chapterId);
        return Result.ok().data("chapter",chapter);
    }

    /**
     * 根据id 修改
     * @param chapter
     * @return
     */
    @ApiOperation(value = "根据id修改")
    @PostMapping("updateChapter")
    public Result update(
            @ApiParam(name = "eduChapter", value = "修改EduChapter 对象", required = true)
            @RequestBody EduChapter chapter){
        boolean flag = chapterService.updateById(chapter);
        return Result.ok();
    }

    /**
     * 根据id删除章节,下有小节不让删
     * @param chapterId
     * @return
     */
    @ApiOperation(value = "根据id删除")
    @DeleteMapping("{chapterId}")
    public Result update(
            @ApiParam(name = "chapterId", value = "根据id删除", required = true)
            @PathVariable String chapterId){
        boolean result = chapterService.deleteChapter(chapterId);
        return result ? Result.ok() : Result.error();
    }
}

