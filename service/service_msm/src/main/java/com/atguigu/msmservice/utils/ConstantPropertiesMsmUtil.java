package com.atguigu.msmservice.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author db
 * @date 2021/2/20 - 15:53
 */
@Component
@PropertySource("classpath:application.properties")
public class ConstantPropertiesMsmUtil implements InitializingBean {

    @Value("${aliyun.msm.send.keyid}")
    private String keyid;
    @Value("${aliyun.msm.send.keysecret}")
    private String keysecret;
    @Value("${aliyun.msm.send.regionid}")
    private String regionid;

    public static String MSM_SEND_KEYID;
    public static String MSM_SEND_KEYSECRENT;
    public static String MSM_SEND_REGIONID;

    @Override
    public void afterPropertiesSet() throws Exception {
        MSM_SEND_KEYID = keyid;
        MSM_SEND_KEYSECRENT = keysecret;
        MSM_SEND_REGIONID = regionid;
    }
}
