package com.jungle.ucenter.service;

import com.jungle.ucenter.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jungle.ucenter.entity.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author jungle
 * @since 2022-03-15
 */
public interface MemberService extends IService<Member> {

    String login(Member member);

    void register(RegisterVo registerVo);

    Member getMemberByOpenid(String openid);

    int getCountRegister(String day);
}
