package com.jungle.serviceedu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jungle.serviceedu.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jungle.serviceedu.entity.frontVo.CourseQueryVo;
import com.jungle.serviceedu.entity.frontVo.CourseWebVo;
import com.jungle.serviceedu.entity.vo.CourseFinal;
import com.jungle.serviceedu.entity.vo.CourseInfoVo;
import com.jungle.serviceedu.entity.vo.CourseQuery;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author jungle
 * @since 2022-03-02
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseIfoById(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CourseFinal getPublishCourseInfo(String courseId);

    void publishCourse(String id);

    void getCourseList(Page<EduCourse> page,CourseQuery courseQuery);

    void deleteCourse(String courseId);

    List<EduCourse> getHotCourse();

    Map<String, Object> getCourseFrontList(Page<EduCourse> coursePage,CourseQueryVo courseQueryVo);

    CourseWebVo getFrontCourseInfoById(String courseId);
}
