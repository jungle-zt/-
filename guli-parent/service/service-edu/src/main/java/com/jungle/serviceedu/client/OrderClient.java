package com.jungle.serviceedu.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name="service-order")
public interface OrderClient {
    @GetMapping("/eduorder/order/getIsBuy/{courseId}//{memberId}")
    public boolean getIsBuy(@PathVariable("courseId") String courseId,@PathVariable("memberId" ) String memberId);
}
