package com.jungle.serviceedu.controller;

import com.jungle.commonUtils.R;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eduservice/user")
public  class EduLoginController {
    @PostMapping("login")
    public R login(){
        return R.ok ().data ( "token","admin" );
    }

    @GetMapping("info")
    public R info(){
        return R.ok ().data ( "roles" ,"[admin]").data ( "name","admin" ).data ( "avatar","abs" );
    }
}
