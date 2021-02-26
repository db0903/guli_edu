package com.atguigu.servicebase.exceptionhandler;

import com.atguigu.commonutils.ExceptionUtil;
import com.atguigu.commonutils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author db
 * @date 2021/1/16 - 23:36
 * ControllerAdvice  统一异常处理
 *
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 所有异常
     * @param e e
     * @return Result
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        return Result.error().message("全局异常处理");
    }
    /**
     *  特定异常处理
     * @param e e
     * @return Result
     */
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Result error(ArithmeticException e){
        e.printStackTrace();
        return Result.error().message("特定异常处理ArithmeticException");
    }
    /**
     * 自定义异常处理
     * @param e e
     * @return  Result
     */
    @ExceptionHandler(GuliException.class)
    @ResponseBody
    public Result error(GuliException e){
        log.error(e.getMessage());
        // 异常日志输出到文件
        log.error(ExceptionUtil.getMessage(e));
        e.printStackTrace();
        return Result.error().message(e.getMsg()).code(e.getCode());
    }
}
