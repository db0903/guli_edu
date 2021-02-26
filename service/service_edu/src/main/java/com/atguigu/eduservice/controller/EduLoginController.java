package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.Result;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

/**
 * @author db
 * @date 2021/1/19 - 19:38
 */
@Api("登录")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@RestController
@RequestMapping("/eduservice/user")
public class EduLoginController {

    /**
     * login
     * @return Result
     */
    @PostMapping("login")
    public Result login(){
        return Result.ok().data("token", "admin");
    }

    /**
     * info
     * @return Result
     */
    @GetMapping("info")
    public Result info(){
        return Result.ok().data("roles", "[admin]").data("name", "admin").data("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
