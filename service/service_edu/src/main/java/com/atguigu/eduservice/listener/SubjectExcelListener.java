package com.atguigu.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.ExcelSubjectData;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.Map;

/**
 * @author db
 * @date 2021/1/25 - 15:14
 */
public class SubjectExcelListener extends AnalysisEventListener<ExcelSubjectData> {

    /**
     * 因为SubjectExcelListener不能交给spring处理，需要自己new，不能注入其他对象,通过构造方法传入参数
     */
    public EduSubjectService subjectService;
    public SubjectExcelListener() { }
    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    /**
     * 一行行读取
     * @param excelSubjectData excelSubjectData
     * @param analysisContext analysisContext
     */
    @Override
    public void invoke(ExcelSubjectData excelSubjectData, AnalysisContext analysisContext) {
        if(excelSubjectData == null){
            throw new GuliException(20001,"文件数据为空");
        }
        //一行一行读取，每次读取两个值第一个一级分类，第二个二级分类
        //判断一级分类
        EduSubject existOneSubject = this.existOneSubject(subjectService, excelSubjectData.getOneSubjectName());
        //没有相同的一级分类
        if(existOneSubject == null){
            existOneSubject = new EduSubject();
            existOneSubject.setParentId("0");
            //一级分类名称
            existOneSubject.setTitle(excelSubjectData.getOneSubjectName());
            subjectService.save(existOneSubject);
        }
        //判断二级分类
        //获取一级分类的id值
        String pid = existOneSubject.getId();
        EduSubject existTwoSubject = this.existTwoSubject(subjectService, excelSubjectData.getTwoSubjectName(),pid);
        //没有相同的二级分类
        if(existTwoSubject == null){
            existTwoSubject = new EduSubject();
            existTwoSubject.setParentId(pid);
            //二级分类名称
            existTwoSubject.setTitle(excelSubjectData.getTwoSubjectName());
            subjectService.save(existTwoSubject);
        }

    }

    /**
     * 判断一级分类不能重复添加
     * @param subjectService subjectService
     * @param name name
     * @return EduSubject
     */
    private EduSubject existOneSubject(EduSubjectService subjectService,String name){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", "0");
        EduSubject onesubject = subjectService.getOne(wrapper);
        return onesubject;
    }

    /**
     * 判断二级分类不能重复添加
     * @param subjectService subjectService
     * @param name name
     * @param pid pid
     * @return EduSubject
     */
    private EduSubject existTwoSubject(EduSubjectService subjectService,String name,String pid){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", pid);
        EduSubject twosubject = subjectService.getOne(wrapper);
        return twosubject;
    }
    /**
     * 读取表头
     * @param headMap headMap
     * @param context context
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
    }
    /**
     * 读取结束
     * @param analysisContext analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    }
}
