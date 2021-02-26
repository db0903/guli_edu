package com.atguigu.eduservice.client;

import com.atguigu.commonutils.Result;
import com.atguigu.eduservice.client.impl.VodFileDegradeFeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author db
 * @date 2021/2/8 - 9:53
 */
@FeignClient(name = "service-vod",fallback = VodFileDegradeFeignClient.class )
@Component
public interface VodClient {
    /**
     * removeAlyVideo 定义方法路径,根据视频id删除阿里云视频
     * @param id 阿里云的视频id
     * @return  Result
     */
    @DeleteMapping("/eduVod/video/removeAlyVideo/{id}")
    public Result removeAlyVideo(@PathVariable("id") String id);
    /**
     * 同使删除多个阿里云视频
     * @param videoIdList videoIdList
     * @return Result
     */
    @DeleteMapping("/eduVod/video/delete-batch")
    public Result deleteBatch(@RequestParam("videoIdList") List<String> videoIdList);
}
