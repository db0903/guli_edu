package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.ExcelSubjectData;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.entity.subject.TwoSubject;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author db
 * @since 2021-01-25
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {
    /**
     * 添加课程分类
     * @param file file
     * @param subjectService subjectService
     */
    @Override
    public void saveSubject(MultipartFile file,EduSubjectService subjectService) {
        try {
            //文件输入流
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, ExcelSubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 课程分类列表属树形
     * @return List
     */
    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        //查询所有的一级分类 parentd_id = 0
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        //eq 表示等于
        wrapperOne.eq("parent_id", 0);
        //接受多条信息
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperOne);
        //查询所有的二级分类 parentd_id != 0
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        //ne 表示不等于
        wrapperTwo.ne("parent_id", 0);
        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapperTwo);
        //创建集合，用于储存最终封装的数据据
        List<OneSubject> finalSubjectList = new ArrayList<>();
        //封装一级分类
        for (int i = 0; i <oneSubjectList.size() ; i++) {
            EduSubject eduSubject = oneSubjectList.get(i);
            OneSubject oneSubject = new OneSubject();
            //eduSubject的值复制到oneSubject中，跟上两行结果相同
            BeanUtils.copyProperties(eduSubject, oneSubject);
            finalSubjectList.add(oneSubject);
            //一级分类中查询二级分类，创建二级分类集合
            List<TwoSubject> finalTwoSubject = new ArrayList<>();
            for (int j = 0; j < twoSubjectList.size(); j++) {
                EduSubject tSubject = twoSubjectList.get(j);
                //判断二级分类与一级分类的id是否一至
                if(tSubject.getParentId().equals(eduSubject.getId())){
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(tSubject, twoSubject);
                    finalTwoSubject.add(twoSubject);
                }
            }
            //把一级分类下面的所有二级分类放在一级分类里面
            oneSubject.setChildren(finalTwoSubject);
        }
        //封装二级分类
        return finalSubjectList;
    }

}
