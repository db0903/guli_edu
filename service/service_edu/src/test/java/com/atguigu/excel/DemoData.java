package com.atguigu.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author db
 * @date 2021/1/25 - 13:37
 */
@Data
public class DemoData implements Serializable {

    @ExcelProperty(value = "学生编号", index = 0) //设置表头名称
    private int sno;

    @ExcelProperty(value = "学生姓名", index = 1)  //设置表头名称
    private String sname;

    @Override
    public String toString() {
        return "DemoData{" +
                "sno=" + sno +
                ", sname='" + sname + '\'' +
                '}';
    }
}
