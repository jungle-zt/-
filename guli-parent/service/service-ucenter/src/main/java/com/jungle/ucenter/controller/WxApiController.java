package com.jungle.ucenter.controller;

import com.google.gson.Gson;
import com.jungle.commonUtils.utils.JwtUtils;
import com.jungle.serviceBase.exception.GuliException;
import com.jungle.ucenter.entity.Member;
import com.jungle.ucenter.service.MemberService;
import com.jungle.ucenter.utils.ConstantPropertiesUtil;
import com.jungle.ucenter.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

@Controller//注意这里没有配置 @RestController
@RequestMapping("/api/ucenter/wx")
public class WxApiController {
    @Autowired
    private MemberService memberService;

    //    获取扫描人信息、
    @GetMapping("callback")
    public String callback(String code,String state) {
//        获取code值，临时票据，类似于验证码
//        请求微信固定地址，得到两个值access_token和oppenid
        String baseAccessTokenUrl =
                "https://api.weixin.qq.com/sns/oauth2/access_token" +
                        "?appid=%s" +
                        "&secret=%s" +
                        "&code=%s" +
                        "&grant_type=authorization_code";
//        拼接三个参数
        String accessTokenUrl = String.format ( baseAccessTokenUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                ConstantPropertiesUtil.WX_OPEN_APP_SECRET,
                code );
//        请求地址,使用httpclient，得到返回结果
        String accessTokenInfo = null;
        try {
            accessTokenInfo = HttpClientUtils.get ( accessTokenUrl );
//            把字符串转为map
            Gson gson = new Gson ();
            HashMap map = gson.fromJson ( accessTokenInfo,HashMap.class );
            String access_token = (String) map.get ( "access_token" );
            String openid = (String) map.get ( "openid" );
//
//            第一次扫码时扫码人信息添加到数据库，自动注册
            Member member = memberService.getMemberByOpenid ( openid );
//            用户不存在添加到数据库直接注册
            if (member == null) {
//                请求地址获取扫码人信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                String userInfoUrl = String.format ( baseUserInfoUrl,access_token,openid );
                String userInfo = HttpClientUtils.get ( userInfoUrl );
                HashMap userInfoMap = gson.fromJson ( userInfo,HashMap.class );
                String nickname = (String) userInfoMap.get ( "nickname" );//微信昵称
                String headimgurl = (String) userInfoMap.get ( "headimgurl" );//头像
                member = new Member ();
                member.setOpenid ( openid );
                member.setAvatar ( headimgurl );
                member.setNickname ( nickname );
                memberService.save ( member );
            }
            // 生成jwt,可以解决跨域问题
            String token = JwtUtils.getJwtToken ( member.getId (),
                    member.getNickname () );
            //存入cookie
            //CookieUtils.setCookie(request, response, "guli_jwt_token", token);
            //因为端口号不同存在蛞蝓问题，cookie不能跨域，所以这里使用url重写
            return "redirect:http://localhost:3000?token="+token;
        } catch (Exception e) {
            e.printStackTrace ();
            throw new GuliException ( 20001,"登录失败" );
        }
    }

    @GetMapping("login")
    public String genQrConnect(HttpSession session) {
        // 微信开放平台授权baseUrl
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        // 回调地址
        String redirectUrl = ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL; //获取业务服务器重定向地址
        try {
            redirectUrl = URLEncoder.encode ( redirectUrl,"UTF-8" ); //url编码
        } catch (UnsupportedEncodingException e) {
            throw new GuliException ( 20001,e.getMessage () );
        }
        // 防止csrf攻击（跨站请求伪造攻击）
        //String state = UUID.randomUUID().toString().replaceAll("-", "");//一般情况下会使用一个随机数
        String state = "imhelen";//为了让大家能够使用我搭建的外网的微信回调跳转服务器，这里填写你在ngrok的前置域名
        System.out.println ( "state = " + state );
        // 采用redis等进行缓存state 使用sessionId为key 30分钟后过期，可配置
        //键："wechar-open-state-" + httpServletRequest.getSession().getId()
        //值：satte
        //过期时间：30分钟

        //生成qrcodeUrl
        String qrcodeUrl = String.format (
                baseUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                redirectUrl,
                state );
        return "redirect:" + qrcodeUrl;
    }
}
