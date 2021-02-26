package com.atguigu.educms.controller;

import com.atguigu.commonutils.Result;
import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author db
 * @since 2021-02-10
 */
@Api("管理员后台")
@RestController
@RequestMapping("/educms/bannerAdmin")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class BannerAdminController {

    @Autowired
    private CrmBannerService crmBannerService;
    /**
     * @param page 页数
     * @param limit  单页的显示条数
     * @param title 查询条件
     * @return Result
     */
    @GetMapping("pageBanner/{page}/{limit}")
    public Result pageBanner(@PathVariable Long page,@PathVariable Long limit,@RequestParam(required = false) String title){
        Page<CrmBanner> bannerPage = new Page<>(page,limit);
        crmBannerService.pageQuery(bannerPage, title);
        List<CrmBanner> list = bannerPage.getRecords();
        long total = bannerPage.getTotal();
        return Result.ok().data("itms",list).data("total",total);
    }
    /**
     * 添加
     * @param crmBanner crmBanner
     * @return  @return
     */
    @PostMapping("addBanner")
    public Result addBanner (@RequestBody CrmBanner crmBanner){
        crmBannerService.save(crmBanner);
        return Result.ok();
    }

    /**
     * 根据id 查询
     * @param id id
     * @return Result
     */
    @GetMapping("get/{id}")
    public Result getBanner (@PathVariable String id){
        CrmBanner crmBanner = crmBannerService.getById(id);
        return Result.ok().data("item", crmBanner);
    }
    /**
     * 修改
     * @param crmBanner  crmBanner
     * @return Result
     */
    @PostMapping ("update")
    public Result updateBanner (@RequestBody CrmBanner crmBanner){
        crmBannerService.updateById(crmBanner);
        return Result.ok();
    }
    /**
     * 删除
     * @param id  id
     * @return Result
     */
    @DeleteMapping("remove/{id}")
    public Result removeBanner (@PathVariable String id){
        crmBannerService.removeById(id);
        return Result.ok();
    }

}

