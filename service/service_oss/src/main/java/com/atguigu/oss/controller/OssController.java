package com.atguigu.oss.controller;

import com.atguigu.commonutils.Result;
import com.atguigu.oss.service.OssService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author db
 * @date 2021/1/22 - 15:53
 */
@Api(description="阿里云文件管理")
@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class OssController {

    @Autowired
    private OssService ossService;

    /**
     * 上传头像的方法
     * @param file file
     * @return Result
     */
    @PostMapping
    public Result uploadOssFile(@RequestParam("file") MultipartFile file){
        String url = ossService.uploadFileAvatar(file);
        return Result.ok().data("url",url);
    }
}
