package com.atguigu.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.atguigu.commonutils.Result;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.atguigu.vod.service.VideoService;
import com.atguigu.vod.utils.ConstantPropertiesVodUtil;
import com.atguigu.vod.utils.InitVodCilent;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

/**
 * @author db
 * @date 2021/1/22 - 15:53
 */
@Api
@RestController
@RequestMapping("/eduVod/video")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class VodController {
    @Autowired
    private VideoService videoService;
    /**
     * @param file  得到上传的文件
     * @return 上传的结果
     */
    @PostMapping("uploadAlyiVideo")
    public Result uploadVideo(@RequestParam("file") MultipartFile file ){
        try {
            //返回上传的id
            String videoId = videoService.uploadVideo(file);
            return Result.ok().data("videoId",videoId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除阿里云视频单个删除
     * @param id 阿里云视频id
     * @return 成功或失败
     */
    @DeleteMapping("removeAlyVideo/{id}")
    public Result removeAlyVideo(@PathVariable String id) {
        try {
            //初始化对象
            DefaultAcsClient client = InitVodCilent.initVodClient(ConstantPropertiesVodUtil.ACCESS_KEY_ID, ConstantPropertiesVodUtil.ACCESS_KEY_SECRET);
            //创建删除视频的request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //request中设置视频id
            request.setVideoIds(id);
            //使用初始化方法删除
            client.getAcsResponse(request);
            return Result.ok();
        } catch (ClientException e) {
            e.printStackTrace();
            throw new GuliException(20001,"删除视频失败");
        }
    }
    /**
     * 阿里云删除多个视频
     * @return Result
     */
    @DeleteMapping("delete-batch")
    public Result deleteBatch(@RequestParam("videoIdList") List<String> videoIdList){
        videoService.removeMoreAlyVideo(videoIdList);
        return Result.ok();
    }
}
