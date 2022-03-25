package com.jungle.statistics.client;

import com.jungle.commonUtils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-ucenter")
public interface UcenterClient {
    @GetMapping("/ucenter/member/getCountRegister/{day}")
    public R getCountRegister(@PathVariable("day") String day);
}
