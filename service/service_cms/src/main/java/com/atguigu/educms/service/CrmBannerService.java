package com.atguigu.educms.service;

import com.atguigu.educms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author db
 * @since 2021-02-10
 */
public interface CrmBannerService extends IService<CrmBanner> {
    /**
     * 查询所有
     * @return  List
     */
    List<CrmBanner> selectAllBanner();
    /**
     * 条件分页查询
     * @param bannerPage bannerPage
     * @param title title
     */
    void pageQuery(Page<CrmBanner> bannerPage, String title);
}
