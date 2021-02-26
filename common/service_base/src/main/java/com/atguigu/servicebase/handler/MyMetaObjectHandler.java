package com.atguigu.servicebase.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import java.util.Date;

/**
 * @author db
 * @date 2021/1/16 - 22:17
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    /**
     * 添加时间,自动填充时间
     * @param metaObject metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        //属性名称
        this.setFieldValByName("gmtCreate",new Date(),metaObject);
        this.setFieldValByName("gmtModified",new Date(),metaObject);
    }

    /**
     * //修改时间
     * @param metaObject metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        //属性名称
        this.setFieldValByName("gmtModified", new Date(), metaObject);
    }
}
