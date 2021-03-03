package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.Result;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author db
 * @since 2021-01-26
 */
@Api("章节小节")
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class EduVideoController {
    @Autowired
    private EduVideoService eduVideoService;
    @Autowired
    private VodClient vodClient ;

    /**
     * 小节添加
     * @param video video
     * @return Result
     */
    @PostMapping("addVideo")
    public Result addVideo(@RequestBody EduVideo video){
        boolean flag = eduVideoService.save(video);
        return flag ? Result.ok() : Result.error();
    }
    /**
     * 小节id查询回显
     * @param videoId videoId
     * @return Result
     */
    @GetMapping("getVideoInfo/{videoId}")
    public Result getVideoInfo(@PathVariable String videoId){
        EduVideo  video = eduVideoService.getById(videoId);
        return Result.ok().data("video",video);
    }
    /**
     * 修改
     * @param video video
     * @return  Result
     */
    @PostMapping("updateVideo")
    public Result updateVideo(@RequestBody EduVideo video){
        boolean flag = eduVideoService.updateById(video);
        return flag ? Result.ok() : Result.error();
    }
    /**
     * 小节删除,小节下的视频也要删除
     * @param videoId videoId
     * @return Result
     */
    @DeleteMapping("{videoId}")
    public Result deleteVideo(@PathVariable String videoId){
        //根据videoId得到视频id
        EduVideo eduVideo = eduVideoService.getById(videoId);
        String videoSourceId = eduVideo.getVideoSourceId();
        if(!StringUtils.isEmpty(videoSourceId)){
            //删除阿里云视频
            Result result = vodClient.removeAlyVideo(videoSourceId);
            if(result.getCode() == 20001){
                throw new GuliException(20001,"删除视频失败，熔断器。。。。");
            }
        }
        //删除数据库信息
        boolean remove = eduVideoService.removeById(videoId);
        return remove ? Result.ok() : Result.error();
    }
}

