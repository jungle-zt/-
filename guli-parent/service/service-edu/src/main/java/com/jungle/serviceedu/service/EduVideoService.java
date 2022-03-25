package com.jungle.serviceedu.service;

import com.jungle.serviceedu.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author jungle
 * @since 2022-03-02
 */
public interface EduVideoService extends IService<EduVideo> {

    void removeVideoByCourseId(String courseId);

    void removeVideoById(String videoId);
}
