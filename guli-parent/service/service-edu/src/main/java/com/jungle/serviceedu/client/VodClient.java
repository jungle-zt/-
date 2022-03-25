package com.jungle.serviceedu.client;

import com.jungle.commonUtils.R;
import com.jungle.serviceedu.client.impl.VodClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient(name = "service-vod",fallback = VodClientImpl.class)
public interface VodClient {
//    调用方法的名称及路径
    @DeleteMapping("/vod/video/{videoId}")
    public R deleteAliyunVideo(@PathVariable("videoId") String videoId);
    @DeleteMapping("/vod/video/deleteBatch")
    public R deleteBatchAliyunVideo(@RequestParam("videoIdList") List<String> videoIdList);
}
