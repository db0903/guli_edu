package com.atguigu.educenter.service;

import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author db
 * @since 2021-02-20
 */
public interface UcenterMemberService extends IService<UcenterMember> {
    /**
     * 用户登陆
     * @param member 用户对象
     * @return String
     */
    String login(UcenterMember member);
    /**
     * 用户注册
     * @param registerVo 注册的实体类对象
     */
    void register(RegisterVo registerVo);
}
