package com.atguigu.educms.service.impl;

import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.mapper.CrmBannerMapper;
import com.atguigu.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.StringUtils;
import java.util.List;
/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 * @author db
 * @since 2021-02-10
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {
    /**
     * 分页查询
     * @param bannerPage bannerPage
     * @param title title
     */
    @Override
    public void pageQuery(Page<CrmBanner> bannerPage, String title) {
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(title)){
            wrapper.like("title", title);
        }
        wrapper.orderByDesc("gmt_modified");
        baseMapper.selectPage(bannerPage,wrapper);
    }
    /**
     * 查询所有的Banner，用户轮播图片
     * @return list
     */

    @Override
    @Cacheable(value = "banner",key = "'selectIndexList:'")
    public List<CrmBanner> selectAllBanner() {
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        // //根据id进行降序排列，显示排列之后前两条记录
        wrapper.last("limit 2");
        List<CrmBanner> list = baseMapper.selectList(null);
        return list;
    }

}
