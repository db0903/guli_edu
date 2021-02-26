package com.atguigu.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.oss.service.OssService;
import com.atguigu.oss.utils.ConstantPropertiesOssUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author db
 * @date 2021/1/22 - 15:54
 */
@Service
public class OssServiceImpl implements OssService {
    /**
     * 上传图片到oss
     * @param file file
     * @return String
     */
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        //工具类获取值
        //地域几点
        String endpoint = ConstantPropertiesOssUtil.END_POINT;
        //id
        String accessKeyId = ConstantPropertiesOssUtil.ACCESS_KEY_ID;
        //密钥
        String accessKeySecret = ConstantPropertiesOssUtil.ACCESS_KEY_SECRET;
        //名字
        String bucketName = ConstantPropertiesOssUtil.BUCKET_NAME;
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            // 上传文件流。
            InputStream inputStream = file.getInputStream();
            //获取文件命名成,文件名字唯一
            String filename = UUID.randomUUID().toString().replace("-", "")+file.getOriginalFilename();
            //调用oss方法实现上传 第一个参数 bucketName，第二个参数,上传的文件名称和文件路径：按照日期进行分组
            String dataPath = new DateTime().toString("yyyy/MM/dd");
            // 按照日期分类创建文件夹
            filename = dataPath+"/"+filename;
            ossClient.putObject(bucketName, filename, inputStream);
            // 关闭OSSClient。
            ossClient.shutdown();
            //返回上传的文件路径 https://edu-db0903.oss-cn-beijing.aliyuncs.com/test.png
            String url = "https://"+bucketName+"."+endpoint+"/"+filename;
            return url;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
