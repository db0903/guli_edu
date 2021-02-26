package com.atguigu.eduservice.client.impl;

import com.atguigu.commonutils.Result;
import com.atguigu.eduservice.client.VodClient;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author db
 * @date 2021/2/9 - 13:16
 */
@Component
public class VodFileDegradeFeignClient implements VodClient {
    /**
     * 熔断出错执行
     * @param id 阿里云的视频id
     * @return Result
     */
    @Override
    public Result removeAlyVideo(String id) {
        return Result.error().message("删除视频出错了");
    }

    /**
     * 熔断出错执行
     * @param videoIdList videoIdList
     * @return Result
     */
    @Override
    public Result deleteBatch(List<String> videoIdList) {
        return Result.error().message("删除多个视频出错了");
    }
}
