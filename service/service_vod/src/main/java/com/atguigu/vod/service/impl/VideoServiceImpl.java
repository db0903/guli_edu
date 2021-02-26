package com.atguigu.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.atguigu.vod.service.VideoService;
import com.atguigu.vod.utils.ConstantPropertiesVodUtil;
import com.atguigu.vod.utils.InitVodCilent;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author db
 * @date 2021/2/7 - 10:17
 */
@Slf4j
@Service
public class VideoServiceImpl implements VideoService {
    Logger logger = LoggerFactory.getLogger(Object.class);
    @Value("${aliyun.vod.file.keyid}")
    private String keyId;
    @Value("${aliyun.vod.file.keysecret}")
    private String keySecret;

    /**
     * @param file 获取上传的文件
     * @return String 上传文件的id
     */
    @Override
    public String uploadVideo(MultipartFile file) {
        try {
            //上传文件输入流
            InputStream inputStream = file.getInputStream();
            //上传文件的原始名
            String originalFilename = file.getOriginalFilename();
            //上传后显示的名称
            String title = originalFilename.substring(0, originalFilename.lastIndexOf("."));
            UploadStreamRequest request = new UploadStreamRequest(ConstantPropertiesVodUtil.ACCESS_KEY_ID, ConstantPropertiesVodUtil.ACCESS_KEY_SECRET,title, originalFilename, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。
            // 其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
            String videoId = response.getVideoId();
            if (!response.isSuccess()) {
                String errorMessage = "阿里云上传错误：" + "code：" + response.getCode() + ", message：" + response.getMessage();
                logger.info(errorMessage);
                if(StringUtils.isEmpty(videoId)){
                    throw new GuliException(20001, errorMessage);
                }
            }
            return videoId;
        } catch (IOException e) {
            throw new GuliException(20001, "guli vod 服务上传失败");
        }
    }

    /**
     * 阿里云删除多个视频
     * @param videoIdList videoIdList
     */
    @Override
    public void removeMoreAlyVideo(List videoIdList) {
        try {
            //初始化对象
            DefaultAcsClient client = InitVodCilent.initVodClient(ConstantPropertiesVodUtil.ACCESS_KEY_ID, ConstantPropertiesVodUtil.ACCESS_KEY_SECRET);
            //创建删除视频的request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //videoIdList
            String videoIds = org.apache.commons.lang.StringUtils.join(videoIdList.toArray(), ",");
            //request中设置视频id1，id2,id3,
            request.setVideoIds(videoIds);
            //使用初始化方法删除
            client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new GuliException(20001,"删除视频失败");
        }
    }

}
