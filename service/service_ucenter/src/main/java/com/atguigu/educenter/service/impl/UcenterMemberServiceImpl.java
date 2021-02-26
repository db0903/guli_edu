package com.atguigu.educenter.service.impl;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.MD5;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.mapper.UcenterMemberMapper;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author db
 * @since 2021-02-20
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    /**
     * 用户登陆
     * @param member 用户对象
     * @return String token
     */
    @Override
    public String login(UcenterMember member) {
        //手机号
        String mobile = member.getMobile();
        //密码
        String password = member.getPassword();
        //参数校验不为空
        if(StringUtils.isEmpty(member) || StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            throw new GuliException(20001,"登录失败");
        }
        //校验用户名
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        UcenterMember ucenterMember = baseMapper.selectOne(wrapper);
        //查询结果为空
        if(StringUtils.isEmpty(ucenterMember) ){
            throw new GuliException(20001,"登录失败");
        }
        //校验密码,MD5加密
        if(!ucenterMember.getPassword().equals(MD5.encrypt(password)) ){
            throw new GuliException(20001,"登录失败");
        }
        //校验是否禁用
        if(ucenterMember.getIsDisabled()){
            throw new GuliException(20001,"禁止登陆");
        }
        //登陆成功 生成token
        String jwtToken = JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());
        return jwtToken;
    }

    /**
     * 用户注册
     * @param registerVo 注册的实体类对象
     */
    @Override
    public void register(RegisterVo registerVo) {
        //手机
        String mobile = registerVo.getMobile();
        //密码
        String password = registerVo.getPassword();
        //昵称
        String nickname = registerVo.getNickname();
        //验证码
        String code = registerVo.getCode();
        //非空判断
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password) || StringUtils.isEmpty(nickname) || StringUtils.isEmpty(code)){
            throw new GuliException(20001, "注册失败");
        }
        //验证码验证，缓存在redis
        String codeRedis = redisTemplate.opsForValue().get(mobile);
        if(!codeRedis.equals(code)){
            throw new GuliException(20001, "注册失败,验证码错误");
        }
        //手机号判断，手机号不重复
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        //判断有没有数据
        Integer count = baseMapper.selectCount(wrapper);
        if(count > 0){
            throw new GuliException(20001, "注册失败,手机号重复");
        }
        //添加数据库
        UcenterMember ucenterMember = new UcenterMember();
        BeanUtils.copyProperties(registerVo, ucenterMember);
        ucenterMember.setPassword(MD5.encrypt(registerVo.getPassword()));
        //默认不禁用
        ucenterMember.setIsDisabled(false);
        baseMapper.insert(ucenterMember);
    }
}
