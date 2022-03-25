package com.jungle.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.jungle.commonUtils.R;
import com.jungle.serviceBase.exception.GuliException;
import com.jungle.vod.service.VodService;
import com.jungle.vod.util.AliyunVodSDKUtils;
import com.jungle.vod.util.ConstantPropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/vod/video")
public class VodController {
    @Autowired
    private VodService vodService;

    @PostMapping("uploadVideo")
    public R uploadVideo(MultipartFile file) {
        String videoId = vodService.uploadVideo ( file );
        return R.ok ().data ( "videoId",videoId );
    }

    @DeleteMapping("{videoId}")
    public R deleteAliyunVideo(@PathVariable("videoId") String videoId) {
        vodService.deleteAliyunVideo ( videoId );
        return R.ok ();
    }

    //    删除多个阿里云视频
//
    @DeleteMapping("deleteBatch")
    public R deleteBatchAliyunVideo(@RequestParam("videoIdList") List<String> videoIdList) {
        vodService.deleteBatchAliyunVideo ( videoIdList );
        return R.ok ();
    }

    //根据视频id获取播放凭证
    @GetMapping("getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable String id) {
        try {
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient ( ConstantPropertiesUtil.ACCESS_KEY_ID,ConstantPropertiesUtil.ACCESS_KEY_SECRET );
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest ();
            request.setVideoId ( id );
            GetVideoPlayAuthResponse response = client.getAcsResponse ( request );
            String playAuth = response.getPlayAuth ();
            return R.ok ().data ( "playAuth",playAuth );
        } catch (Exception e){
            throw new GuliException (20001,"获取凭证失败");
        }
    }
}
