package com.jungle.serviceedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jungle.serviceBase.exception.GuliException;
import com.jungle.serviceedu.entity.EduCourse;
import com.jungle.serviceedu.entity.EduCourseDescription;
import com.jungle.serviceedu.entity.frontVo.CourseQueryVo;
import com.jungle.serviceedu.entity.frontVo.CourseWebVo;
import com.jungle.serviceedu.entity.vo.CourseFinal;
import com.jungle.serviceedu.entity.vo.CourseInfoVo;
import com.jungle.serviceedu.entity.vo.CourseQuery;
import com.jungle.serviceedu.mapper.EduCourseMapper;
import com.jungle.serviceedu.service.EduChapterService;
import com.jungle.serviceedu.service.EduCourseDescriptionService;
import com.jungle.serviceedu.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jungle.serviceedu.service.EduVideoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author jungle
 * @since 2022-03-02
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    //    课程描述注入
    @Autowired
    EduCourseDescriptionService courseDescriptionService;
    @Autowired
    EduVideoService videoService;
    @Autowired
    EduChapterService chapterService;

    /**
     * 添加课程信息
     *
     * @param courseInfoVo 课程参数封装
     */
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
//        像课程表中添加数据
        EduCourse eduCourse = new EduCourse ();
        BeanUtils.copyProperties ( courseInfoVo,eduCourse );
        int insert = baseMapper.insert ( eduCourse );
        if (insert <= 0) {
            throw new GuliException ( 20001,"添加课程信息失败" );
        }
//        像描述表中添加数据
        EduCourseDescription courseDescription = new EduCourseDescription ();
//        描述的id与课程id相同
        courseDescription.setId ( eduCourse.getId () );
        courseDescription.setDescription ( courseInfoVo.getDescription () );
        courseDescriptionService.save ( courseDescription );
        return eduCourse.getId ();
    }

    @Override
    public CourseInfoVo getCourseIfoById(String courseId) {
//        查询课程表
        CourseInfoVo courseInfoVo = new CourseInfoVo ();

        EduCourse eduCourse = baseMapper.selectById ( courseId );
        BeanUtils.copyProperties ( eduCourse,courseInfoVo );
//        查询课程描述表
        EduCourseDescription courseDescription = courseDescriptionService.getById ( courseId );
        courseInfoVo.setDescription ( courseDescription.getDescription () );
        return courseInfoVo;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
//        修改课程表
        EduCourse eduCourse = new EduCourse ();
        BeanUtils.copyProperties ( courseInfoVo,eduCourse );
        int update = baseMapper.updateById ( eduCourse );
        if (update == 0) {
            throw new GuliException ( 20001,"修改信息失败" );
        }
        EduCourseDescription eduCourseDescription = new EduCourseDescription ();
        BeanUtils.copyProperties ( courseInfoVo,eduCourseDescription );
        courseDescriptionService.updateById ( eduCourseDescription );
    }

    @Override
    public CourseFinal getPublishCourseInfo(String courseId) {
        return baseMapper.selectCoursePublishVoById ( courseId );

    }

    @Override
    public void publishCourse(String id) {
        EduCourse eduCourse = new EduCourse ();
        eduCourse.setId ( id );
        eduCourse.setStatus ( "Normal" );
        baseMapper.updateById ( eduCourse );
    }

    @Override
    public void getCourseList(Page<EduCourse> eduCoursePage,CourseQuery courseQuery) {

        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<> ();
        if (courseQuery == null) {
            baseMapper.selectPage ( eduCoursePage,queryWrapper );
            return;
        }
        String title = courseQuery.getTitle ();
        String teacherId = courseQuery.getTeacherId ();
        String subjectId = courseQuery.getSubjectId ();
        String subjectParentId = courseQuery.getSubjectParentId ();
        if (!StringUtils.isEmpty ( title )) {
            queryWrapper.like ( "title",title );
        }
        if (!StringUtils.isEmpty ( teacherId )) {
            queryWrapper.eq ( "teacher_id",teacherId );
        }
        if (!StringUtils.isEmpty ( subjectId )) {
            queryWrapper.eq ( "subject_id",subjectId );
        }
        if (!StringUtils.isEmpty ( subjectParentId )) {
            queryWrapper.eq ( "subject_parent_id",subjectParentId );
        }
        baseMapper.selectPage ( eduCoursePage,queryWrapper );
    }

    @Override
    public void deleteCourse(String courseId) {

        //根据课程id删除小节
        videoService.removeVideoByCourseId ( courseId );
//        删除章节
        chapterService.removeChapterByCourseId ( courseId );
//        删除描述
        courseDescriptionService.removeById ( courseId );
//        删除课程本身
        int result = baseMapper.deleteById ( courseId );
        if (result == 0) {
            throw new GuliException ( 20001,"删除课程失败" );
        }
    }

    @Cacheable(value = "course", key = "'hotCourse'")//整合redis缓存
    @Override
    public List<EduCourse> getHotCourse() {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<> ();
        wrapper.orderByDesc ( "buy_count" );
        wrapper.last ( "limit 8" );
        return baseMapper.selectList ( wrapper );
    }

    @ApiOperation (value = "前台课程分页查询显示")
    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> coursePage,CourseQueryVo courseQuery) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<> ();
        if (!StringUtils.isEmpty ( courseQuery.getSubjectParentId () )) {
            queryWrapper.eq ( "subject_parent_id",
                    courseQuery.getSubjectParentId () );
        }
        if (!StringUtils.isEmpty ( courseQuery.getSubjectId () )) {
            queryWrapper.eq ( "subject_id",courseQuery.getSubjectId () );
        }
        if (!StringUtils.isEmpty ( courseQuery.getBuyCountSort () )) {
            queryWrapper.orderByDesc ( "buy_count" );
        }
        if (!StringUtils.isEmpty ( courseQuery.getGmtCreateSort () )) {
            queryWrapper.orderByDesc ( "gmt_create" );
        }
        if (!StringUtils.isEmpty ( courseQuery.getPriceSort () )) {
            queryWrapper.orderByDesc ( "price" );
        }
        baseMapper.selectPage ( coursePage,queryWrapper );
        List<EduCourse> records = coursePage.getRecords ();
        long current = coursePage.getCurrent ();
        long pages = coursePage.getPages ();
        long size = coursePage.getSize ();
        long total = coursePage.getTotal ();
        boolean hasNext = coursePage.hasNext ();
        boolean hasPrevious = coursePage.hasPrevious ();
        Map<String, Object> map = new HashMap<String, Object> ();
        map.put ( "items",records );
        map.put ( "current",current );
        map.put ( "pages",pages );
        map.put ( "size",size );
        map.put ( "total",total );
        map.put ( "hasNext",hasNext );
        map.put ( "hasPrevious",hasPrevious );
        return map;
    }

    @Override
    public CourseWebVo getFrontCourseInfoById(String courseId) {
        return baseMapper.getFrontCourseInfoById(courseId);
    }
}
