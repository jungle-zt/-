package com.jungle.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jungle.commonUtils.utils.JwtUtils;
import com.jungle.commonUtils.utils.MD5;
import com.jungle.serviceBase.exception.GuliException;
import com.jungle.ucenter.entity.Member;
import com.jungle.ucenter.entity.RegisterVo;
import com.jungle.ucenter.mapper.MemberMapper;
import com.jungle.ucenter.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author jungle
 * @since 2022-03-15
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {
    @Autowired
   private RedisTemplate<String,String> redisTemplate;

    // 登录
    @Override
    public String login(Member member) {
        String mobile = member.getMobile ();
        String password = member.getPassword ();
        if (StringUtils.isEmpty ( mobile ) || StringUtils.isEmpty ( password )) {
            throw new GuliException ( 20001,"用户名或密码不能为空" );
        }
        QueryWrapper<Member> wrapper = new QueryWrapper<> ();
        wrapper.eq ( "mobile",mobile );
        Member selectMember = baseMapper.selectOne ( wrapper );
        if (selectMember == null) {
            throw new GuliException ( 20001,"用户不存在" );
        }
        if (!selectMember.getPassword ().equals ( MD5.encrypt ( password ) )) {
            throw new GuliException ( 20001,"密码错误" );
        }
        if (selectMember.getIsDisabled ()) {
            throw new GuliException ( 20001,"用户不存在" );
        }
//        生成token
        String token = JwtUtils.getJwtToken ( selectMember.getId (),selectMember.getNickname () );
        return token;
    }

    @Override
    public void register(RegisterVo registerVo) {
        String nickname = registerVo.getNickname ();
        String mobile = registerVo.getMobile ();
        String code = registerVo.getCode ();
        String password = registerVo.getPassword ();
        if (StringUtils.isEmpty ( nickname ) ||
                StringUtils.isEmpty ( mobile ) ||
                StringUtils.isEmpty ( password ) ||
                StringUtils.isEmpty ( code )) {
            throw new GuliException ( 20001,"请重新输入" );
        }
        String redisCode = redisTemplate.opsForValue ().get ( mobile );
        if(redisCode == null || !redisCode.equals ( code ) ){
            throw new GuliException ( 20001,"验证码错误" );
        }
//        判断手机号是否存在 判断昵称是否存在
        QueryWrapper<Member> wrapper = new QueryWrapper<> ();
        wrapper.eq ( "mobile",mobile );
        wrapper.eq ( "nickname",nickname );
        Integer count = baseMapper.selectCount ( wrapper );
        if(count >= 1){
            throw new GuliException ( 20001,"该账户已存在" );
        }
//
        QueryWrapper<Member> wrapper1 = new QueryWrapper<> ();

        wrapper1.eq ( "nickname",nickname );
        Integer count1 = baseMapper.selectCount ( wrapper1 );
        if(count1 >= 1){
            throw new GuliException ( 20001,"该昵称已存在" );
        }
//
        Member member = new Member ();
        member.setMobile ( mobile );
        member.setPassword (MD5.encrypt ( password ));
        member.setNickname ( nickname );
        member.setAvatar ( "http://thirdwx.qlogo.cn/mmopen/vi_32/zZfLXcetf2Rpsibq6HbPUWKgWSJHtha9y1XBeaqluPUs6BYicW1FJaVqj7U3ozHd3iaodGKJOvY2PvqYTuCKwpyfQ/132" );
        baseMapper.insert ( member );
    }
//    "根据openid查询用户"
    @Override
    public Member getMemberByOpenid(String openid) {
        QueryWrapper<Member> wrapper = new QueryWrapper<> ();
        wrapper.eq ( "openid",openid );
        Member member = baseMapper.selectOne ( wrapper );
        return member;
    }
//根据日期查询注册人数
    @Override
    public int getCountRegister(String day) {
        return baseMapper.getCountRegister(day);

    }
}
