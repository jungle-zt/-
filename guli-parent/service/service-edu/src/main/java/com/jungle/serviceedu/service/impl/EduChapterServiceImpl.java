package com.jungle.serviceedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jungle.serviceBase.exception.GuliException;
import com.jungle.serviceedu.entity.EduChapter;
import com.jungle.serviceedu.entity.EduVideo;
import com.jungle.serviceedu.entity.chapter.ChapterVo;
import com.jungle.serviceedu.entity.chapter.VideoVo;
import com.jungle.serviceedu.mapper.EduChapterMapper;
import com.jungle.serviceedu.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jungle.serviceedu.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author jungle
 * @since 2022-03-02
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Autowired
    EduVideoService videoService;

    @Override
    public List<ChapterVo> getChapterVideoById(String courseId) {
//       根据课程id查询所有的课程章节
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<> ();
        wrapperChapter.eq ( "course_id",courseId );
        List<EduChapter> eduChapters = baseMapper.selectList ( wrapperChapter );
//        根据课程id查询所有的小结
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<> ();
        wrapperVideo.eq ( "course_id",courseId );
        List<EduVideo> eduVideos = videoService.list ( wrapperVideo );

//        封装章节
        List<ChapterVo> chapterVos = new ArrayList<> ();
        for (EduChapter eduChapter : eduChapters) {
            ChapterVo chapterVo = new ChapterVo ();
            BeanUtils.copyProperties (eduChapter,chapterVo );
            chapterVos.add ( chapterVo );
//        封装小节
            ArrayList<VideoVo> videoVos = new ArrayList<> ();
            for (EduVideo eduVideo : eduVideos) {
                if(eduVideo.getChapterId ().equals ( eduChapter.getId () )){
                    VideoVo videoVo = new VideoVo ();
                    BeanUtils.copyProperties ( eduVideo,videoVo );
                    videoVos.add ( videoVo );
                }
            }
            chapterVo.setChildren ( videoVos );
        }

        return chapterVos;
    }

    @Override
    public boolean deleteChapter(String chapterId) {
//        根据id查小节表
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<> ();
        wrapper.eq("chapter_id",chapterId);
        int count = videoService.count ( wrapper );
        if(count > 0){
            throw new GuliException (20001,"请先删除小节");
        }else {
            int result = baseMapper.deleteById ( chapterId );
            return result > 0;
        }

    }

    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> queryWrapper = new QueryWrapper<> ();
        queryWrapper.eq (  "course_id",courseId);
        baseMapper.delete ( queryWrapper );
    }
}
