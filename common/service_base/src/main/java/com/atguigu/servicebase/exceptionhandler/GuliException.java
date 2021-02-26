package com.atguigu.servicebase.exceptionhandler;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author db
 * @date 2021/1/17 - 0:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuliException extends  RuntimeException {
    @ApiModelProperty(value = "状态码")
    private Integer code;

    @ApiModelProperty(value = "异常信息")
    private String msg;

    @Override
    public String toString() {
        return "GuliException{" + "message=" + this.getMessage() + ", code=" + code + '}';
    }
}
