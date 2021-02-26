package com.atguigu.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author db
 * @date 2021/1/25 - 13:40
 */
public class TestEasyExcel {
    public static void main(String[] args) {
        //实现excel 写的操作
        //String filename = "E:\\EasyExcel\\write.xlsx"; //设置写入路径和文件名称
        //EasyExcel.write(filename,DemoData.class).sheet("学生列表").doWrite(getDate()); //调用方法写入
        //实现excel 读的操作
        String filename = "E:\\EasyExcel\\write.xlsx"; //设置读取路径和文件名称
        EasyExcel.read(filename,DemoData.class,new ExcelListener()).sheet().doRead();
    }
    //创建list方法返回集合
    private static List<DemoData> getDate(){
        List<DemoData> list = new ArrayList<>();
        for (int i = 0; i <10 ; i++) {
            DemoData data = new DemoData();
            data.setSname("lucy"+i);
            list.add(data);
        }
        return list;
    }
}
