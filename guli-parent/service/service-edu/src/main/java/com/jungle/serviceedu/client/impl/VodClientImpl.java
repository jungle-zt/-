package com.jungle.serviceedu.client.impl;

import com.jungle.commonUtils.R;
import com.jungle.serviceedu.client.VodClient;
import org.springframework.stereotype.Component;

import java.util.List;
//熔断之后执行
@Component

public class VodClientImpl implements VodClient {
    @Override
    public R deleteAliyunVideo(String videoId) {
        return R.error ().message ( "删除课时视频出错" );
    }

    @Override
    public R deleteBatchAliyunVideo(List<String> videoIdList) {
        return R.error ().message ( "删除课程视频出错" );
    }
}
