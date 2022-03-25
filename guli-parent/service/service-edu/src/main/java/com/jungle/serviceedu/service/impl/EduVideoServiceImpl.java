package com.jungle.serviceedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jungle.commonUtils.R;
import com.jungle.serviceBase.exception.GuliException;
import com.jungle.serviceedu.client.VodClient;
import com.jungle.serviceedu.entity.EduVideo;
import com.jungle.serviceedu.mapper.EduVideoMapper;
import com.jungle.serviceedu.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author jungle
 * @since 2022-03-02
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {
    @Autowired
    VodClient vodClient;

    @Override
    public void removeVideoByCourseId(String courseId) {
//        删除所有视频
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<> ();

        wrapperVideo.eq ( "course_id",courseId );
        wrapperVideo.select ( "video_source_id" );
        List<EduVideo> eduVideos = baseMapper.selectList ( wrapperVideo );
        ArrayList<String> videoIds = new ArrayList<> ();
        for (EduVideo eduVideo : eduVideos) {
            String videoSourceId = eduVideo.getVideoSourceId ();
            if(!StringUtils.isEmpty ( videoSourceId )){
                videoIds.add ( videoSourceId );
            }

        }
        if(videoIds.size ()>0){

            R r = vodClient.deleteBatchAliyunVideo ( videoIds );
            if(r.getCode () == 20001){
                throw new GuliException (20001,"删除视频失败，熔断器......");
            }
        }
        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<> ();
        queryWrapper.eq ( "course_id",courseId );


        baseMapper.delete ( queryWrapper );
    }

    @Override
    public void removeVideoById(String videoId) {
//        查询视频id
        EduVideo eduVideo = baseMapper.selectById ( videoId );
        String videoSourceId = eduVideo.getVideoSourceId ();

//        删除视频
        if(!StringUtils.isEmpty ( videoSourceId )){

            R r = vodClient.deleteAliyunVideo ( videoSourceId );
            if(r.getCode () == 20001){
                throw new GuliException (20001,"删除视频失败，熔断器......");
            }
        }
        System.out.println ("===========================");
        System.out.println (videoSourceId);
//        删除小节
        int delete = baseMapper.deleteById ( videoId );

    }

}
