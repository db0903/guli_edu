package com.atguigu.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author db
 * @date 2021/2/7 - 10:07
 */
public interface VideoService {
    /**
     * @param file 获取上传的文件
     * @return 返回 shang传文件的id
     */
    String uploadVideo(MultipartFile file);
    /**
     * 阿里云删除多个视频
     * @param videoIdList videoIdList
     */
    void removeMoreAlyVideo(List videoIdList);
}
