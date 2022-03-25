package com.jungle.serviceedu.service;

import com.jungle.serviceedu.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jungle.serviceedu.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author jungle
 * @since 2022-03-02
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideoById(String courseId);

    boolean deleteChapter(String chapterId);

    void removeChapterByCourseId(String courseId);
}
