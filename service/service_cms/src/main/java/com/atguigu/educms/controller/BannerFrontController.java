package com.atguigu.educms.controller;

import com.atguigu.commonutils.Result;
import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.service.CrmBannerService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 * @author db
 * @since 2021-02-10
 */
@Api("普通用户前台")
@RestController
@RequestMapping("/educms/bannerFront")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class BannerFrontController {
    @Autowired
    private CrmBannerService bannerService;
    /**
     * 查询所有的Banner
     * @return Result
     */
    @GetMapping("getAllBanner")
    public Result getAllBanner() {
        List<CrmBanner> list = bannerService.selectAllBanner();
        return Result.ok().data("list",list);
    }
}

