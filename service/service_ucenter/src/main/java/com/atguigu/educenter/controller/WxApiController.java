package com.atguigu.educenter.controller;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.educenter.utils.ConstantWxUtils;
import com.atguigu.educenter.utils.HttpClientUtils;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @author db
 * @date 2021/3/1 - 10:07
 */
@Api("微信二维码")
@Slf4j
@Controller
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@RequestMapping("/api/ucenter/wx")
public class WxApiController {
    private static final Logger logger = LoggerFactory.getLogger(WxApiController.class);

    @Autowired
    private UcenterMemberService memberService;
    /**
     * 只请求地址，不需要返回数据，只需要Controller
     * @return 地址
     */
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

    /**
     * 获取扫码人的信息，为了测试使用，实际开发部署到服务器
     * @return 地址
     */
    @GetMapping("callback")
    public String callback(String code , String state){
        //获取code值 ，临时票据，；类似与验证码
        logger.info("code:"+code);
        logger.info("state:"+state);
        try {
        //从redis中将state获取出来，和当前传入的state作比较
        //如果一致则放行，如果不一致则抛出异常：非法访问
        //拿着code请求 微信的固定地址，得到两个值 access_token 和 openid
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s"+
                "&secret=%s"+
                "&code=%s"+
                "&grant_type=authorization_code";
        //拼接三个参数 id 密钥  code 值
        String accessTokenUrl = String.format(
                baseAccessTokenUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                ConstantWxUtils.WX_OPEN_APP_SECRET,
                code
            );
        //请求这个拼接好的地址，得到返回的两个值 access_token 和 openid
        //使用HttpClientUtils 发送请求 得到返回结果
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
            logger.info("accessTokenInfo:"+accessTokenInfo);
            //accessTokenInfo json 字符串获取 access_token openid
            //把accessTokenInfo字符串转换map集合，根据map里面key获取对应值
            //使用json转换工具 Gson
            Gson gson = new Gson();
            HashMap mapAccessToken = gson.fromJson(accessTokenInfo, HashMap.class);
            String access_token = (String) mapAccessToken.get("access_token");
            String openid = (String) mapAccessToken.get("openid");
            //微信注册，把扫描人信息添加到数据库里面，需要判断数据表中是否存在微信信息 根据openid判断
            UcenterMember  ucenterMember = memberService.getOpenIdMember(openid);
            //ucenterMember是空，表没有相同微信数据，进行添加
            if(StringUtils.isEmpty(ucenterMember)){
                //3。拿着得到accsess_token 和 openid，再去请求微信提供固定的地址，获取到扫描人信息
                //访问微信的资源服务器，获取用户信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" + "?access_token=%s" + "&openid=%s";
                //拼接两个参数
                String userInfoUrl = String.format( baseUserInfoUrl,access_token, openid );
                //发送请求，返回的用户信息
                String userInfo = HttpClientUtils.get(userInfoUrl);
                logger.info("userInfo:"+userInfo);
                HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
                //获得微信名字
                String nickname = (String)userInfoMap.get("nickname");
                //获得微信头像
                String headimgurl = (String)userInfoMap.get("headimgurl");
                ucenterMember = new UcenterMember();
                ucenterMember.setOpenid(openid);
                ucenterMember.setNickname(nickname);
                ucenterMember.setAvatar(headimgurl);
                memberService.save(ucenterMember);
            }
            //jwt 生成token信息
            String jwtToken = JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());
            return "redirect:http://localhost:3000?token="+jwtToken;
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20001,"登录失败");
        }
    }
}
