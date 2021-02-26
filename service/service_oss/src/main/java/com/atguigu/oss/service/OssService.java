package com.atguigu.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author db
 * @date 2021/1/22 - 15:54
 */
public interface OssService {

    /**
     * 上传图片到oss
     * @param file file
     * @return String
     */
    String uploadFileAvatar(MultipartFile file);
}
