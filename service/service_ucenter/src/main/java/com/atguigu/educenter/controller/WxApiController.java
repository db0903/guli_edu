package com.atguigu.educenter.controller;

import com.atguigu.educenter.utils.ConstantWxUtils;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.net.URLEncoder;

/**
 * 只请求地址，不需要返回数据，只需要Controller
 * @author db
 * @date 2021/3/1 - 10:07
 */
@Api("微信二维码")
@Controller
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@RequestMapping("/api/ucenter/wx")
public class WxApiController {
    @GetMapping("login")
    public String getWxCode(){
        //固定地址。后面拼接参数 %s 相当与一个占位符
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect"+
                        "?appid=%s" +
                        "&redirect_uri=%s" +
                        "&response_type=code" +
                        "&scope=snsapi_login" +
                        "&state=%s" +
                        "#wechat_redirect";
        //redirect_url 进行编码
        String  redirectUrl = ConstantWxUtils.WX_OPEN_REDIRECT_URL;
        try {
            redirectUrl = URLEncoder.encode(redirectUrl,"utf-8");
            //设置%s里面的值
        } catch (Exception e) {
            e.printStackTrace();
        }
        String url = String.format(baseUrl, ConstantWxUtils.WX_OPEN_APP_ID, redirectUrl, "atguigu");
        //重定向到请求微信地址
        return "redirect:"+url;
    }
}
