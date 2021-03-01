package com.atguigu.educenter.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.Result;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.api.R;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author db
 * @since 2021-02-20
 */
@Api("用户登陆注册")
@RestController
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@RequestMapping("/educenter/member")
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService ucenterMemberService;

    /**
     * 用户名密码登陆
     * @param member 用户对象
     * @return 结果
     */
    @PostMapping("login")
    public Result login(@RequestBody UcenterMember member){
        //返回token值使用 jwt 生成
        String token = ucenterMemberService.login(member);
        return Result.ok().data("token",token);
    }

    /**
     * 用户注册成
     * @param registerVo 注册的实体类对象
     * @return 结果
     */
    @PostMapping("register")
    public Result register(@RequestBody RegisterVo registerVo ){
        ucenterMemberService.register(registerVo);
        return Result.ok();
    }
    /**
     * 根据token信息返回用户信息
     * @param request request
     * @return Result
     */
    @GetMapping("getMemberInfo")
    public Result getMemberInfo(HttpServletRequest request){
        //调用jwt工具类，根据request对象获取头信息,反回用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //根据用户id查询用户信息
        UcenterMember member = ucenterMemberService.getById(memberId);
        return Result.ok().data("userInfo",member);
    }
}

