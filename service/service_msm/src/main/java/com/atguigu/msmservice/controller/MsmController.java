package com.atguigu.msmservice.controller;

import com.atguigu.commonutils.Result;
import com.atguigu.msmservice.service.MsmService;
import com.atguigu.msmservice.utils.RandomUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author db
 * @date 2021/2/19 - 16:33
 */
@Api("阿里云短信服务")
@RestController
@RequestMapping("/edumsm/msm")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class MsmController {
    @Autowired
    private MsmService msmService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 发送短信的方法
     *
     * @param phone phone 手机号码
     * @return 结果
     */
    @GetMapping("send/{phone}")
    public Result sendMsm(@PathVariable String phone) {
        //先从redis取出验证码，如果没有直接返回
        String coude = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(coude)) {
           return Result.ok();
        }
        //生成随机数传递阿里云发送
        String code = RandomUtil.getFourBitRandom();
        Map<String, Object> param = new HashMap<>();
        param.put("code", code);
        //调用service 发送短信的方法
        Boolean isSend = msmService.send(param, phone);
        if (isSend) {
            //加入redis,有效时间5分钟
            redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
            return Result.ok();
        } else {
            return Result.error().message("短信发送失败");
        }
    }
}
