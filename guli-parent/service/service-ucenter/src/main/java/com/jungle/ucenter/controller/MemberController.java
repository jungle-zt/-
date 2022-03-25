package com.jungle.ucenter.controller;


import com.jungle.commonUtils.utils.JwtUtils;
import com.jungle.commonUtils.R;
import com.jungle.commonUtils.vo.UcenterMember;
import com.jungle.ucenter.entity.Member;
import com.jungle.ucenter.entity.RegisterVo;
import com.jungle.ucenter.service.MemberService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author jungle
 * @since 2022-03-15
 */
@RestController
@RequestMapping("/ucenter/member")
public class MemberController {
    @Autowired
    private MemberService memberService;


    @ApiOperation(value = "会员登录")
    @PostMapping("login")
    public R login(@RequestBody Member member) {
//        登录成功返回token
        String token = memberService.login ( member );
        return R.ok ().data ( "token",token );
    }

    @ApiOperation(value = "会员注册")
    @PostMapping("register")
    public R register(@RequestBody RegisterVo registerVo) {
//        登录成功返回token

        memberService.register ( registerVo );

        return R.ok ();
    }

    @ApiOperation(value = "根据token获取用户信息")
    @GetMapping("getMemberInfo")
    public R getMemberByToken(HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken ( request );
        Member member = memberService.getById ( memberId );
        return R.ok ().data ( "loginMember",member );
    }

    @ApiOperation(value = "根据id获取用户对象")
    @GetMapping("getMemberById/{id}")
    public UcenterMember getMemberById(@PathVariable("id") String id) {
        Member member = memberService.getById ( id );
        UcenterMember ucenterMember = new UcenterMember ();
        BeanUtils.copyProperties ( member,ucenterMember );
        return ucenterMember;
    }

    //    查询某一天注册人数
    @GetMapping("getCountRegister/{day}")
    public R getCountRegister(@PathVariable String day) {
        int count = memberService.getCountRegister ( day );
        return R.ok ().data ( "count",count );
    }
}

