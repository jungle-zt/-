package com.jungle.serviceedu.controller;


import com.jungle.commonUtils.R;
import com.jungle.serviceedu.entity.EduChapter;
import com.jungle.serviceedu.entity.chapter.ChapterVo;
import com.jungle.serviceedu.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author jungle
 * @since 2022-03-02
 */
@RestController
@RequestMapping("/eduservice/chapter")
public class EduChapterController {
    @Autowired
    EduChapterService chapterService;

    @GetMapping("getChapterVideo/{courseId}")
    private R getChapterVideo(@PathVariable String courseId){
        List<ChapterVo> list = chapterService.getChapterVideoById(courseId);
        return R.ok ().data ( "chapter",list );
    }

    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter){
        chapterService.save ( eduChapter );
        return R.ok ();
    }
    @GetMapping("{chapterId}")
    public R getChapterInfoById(@PathVariable String chapterId){
        EduChapter eduChapter = chapterService.getById ( chapterId );
        return R.ok ().data ( "chapter",eduChapter );

    }
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter){
        chapterService.updateById ( eduChapter );
        return R.ok ();

    }
    @DeleteMapping("{chapterId}")
    public R deleteChapter(@PathVariable String chapterId){
        boolean isDelete = chapterService.deleteChapter ( chapterId );
        if(isDelete){

            return R.ok ();
        }else {
            return R.error ();
        }
    }
}

