package com.jungle.msm.controller;

import com.jungle.commonUtils.R;
import com.jungle.commonUtils.utils.RandomUtil;
import com.jungle.msm.service.MsmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/msm/")
public class MsmApiController {
    @Autowired
    private MsmService msmService;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @GetMapping("send/{phone}")
    public R code(@PathVariable String phone){
        String code = redisTemplate.opsForValue().get(phone);
        if(!StringUtils.isEmpty(code)) return R.ok();
        code = RandomUtil.getFourBitRandom();

        boolean isSend = msmService.send(phone, code);
        if(isSend) {
            redisTemplate.opsForValue().set(phone, code,5,TimeUnit.MINUTES);
            return R.ok();
        } else {
            return R.error().message("发送短信失败");
        }
    }
}
