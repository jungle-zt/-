package com.jungle.order.client;

import com.jungle.commonUtils.vo.UcenterMember;
import com.jungle.order.client.impl.UcenterClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name="service-ucenter",fallback = UcenterClientImpl.class)
public interface UcenterClient {
    @GetMapping("/ucenter/member/getMemberById/{id}")
    public UcenterMember getMemberById(@PathVariable("id") String id) ;
}
