package com.jungle.serviceedu.controller;


import com.jungle.commonUtils.R;
import com.jungle.serviceedu.entity.EduVideo;
import com.jungle.serviceedu.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author jungle
 * @since 2022-03-02
 */
@RestController
@RequestMapping("/eduservice/video")
public class EduVideoController {
    @Autowired
    EduVideoService videoService;

    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo video) {
        videoService.save ( video );
        return R.ok ();
    }
//    删除小节同时删除视频
    @DeleteMapping("{videoId}")
    public R deleteVideo(@PathVariable String videoId){
        videoService.removeVideoById ( videoId );
        return R.ok ();
    }
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo video){
        videoService.updateById ( video );
        return R.ok ();
    }
    @GetMapping("{videoId}")
    public R getVideo(@PathVariable String videoId){
        EduVideo video = videoService.getById ( videoId );
        return R.ok ().data ( "video",video );
    }
}

