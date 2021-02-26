package com.atguigu.msmservice.service;

import java.util.Map;

/**
 * @author db
 * @date 2021/2/19 - 16:34
 */
public interface MsmService {
    /**
     * 发送短信的方法
     * @param param 包含验证码
     * @param phone 手机号
     * @return boolean
     */
    Boolean send(Map<String, Object> param, String phone);
}
