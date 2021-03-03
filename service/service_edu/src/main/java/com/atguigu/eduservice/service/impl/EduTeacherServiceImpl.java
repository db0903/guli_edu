package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.mapper.EduTeacherMapper;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-01-15
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {
    /**
     * 分页返回所有的数据,查询讲师
     * @param pageTeacher 分页数据
     * @return map
     */
    @Override
    public Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageTeacher) {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        //分页封装pageTeacher对象
        baseMapper.selectPage(pageTeacher,wrapper);
        List<EduTeacher> records = pageTeacher.getRecords();
        //当前页的数据条数
        long current = pageTeacher.getCurrent();
        //有多少页
        long pages = pageTeacher.getPages();
        //总数据数量
        long total = pageTeacher.getTotal();
        //每页数据的数量
        long size = pageTeacher.getSize();
        //是否有下一页
        boolean hasNext = pageTeacher.hasNext();
        //是否有上一页
        boolean hasPrevious = pageTeacher.hasPrevious();
        //获取分页数据，放到map集合中
        Map<String,Object> map = new HashMap<>();
        map.put("current",current);
        map.put("pages",pages);
        map.put("total",total);
        map.put("size",size);
        map.put("hasNext",hasNext);
        map.put("hasPrevious",hasPrevious);
        map.put("items", records);
        return map;
    }
}
