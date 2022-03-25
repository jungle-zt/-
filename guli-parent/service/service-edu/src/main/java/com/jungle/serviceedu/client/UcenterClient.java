package com.jungle.serviceedu.client;

import com.jungle.commonUtils.vo.UcenterMember;
import com.jungle.serviceedu.client.impl.UcenterClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name="service-ucenter",fallback = UcenterClientImpl.class)
public interface UcenterClient{
        //根据用户id获取用户信息
        @GetMapping("/ucenter/member/getMemberById/{id}")
        public UcenterMember getMemberById(@PathVariable("id") String
        id);

}
