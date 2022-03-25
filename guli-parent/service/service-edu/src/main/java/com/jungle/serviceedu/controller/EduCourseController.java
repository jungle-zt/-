package com.jungle.serviceedu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jungle.commonUtils.R;
import com.jungle.serviceedu.entity.EduCourse;
import com.jungle.serviceedu.entity.vo.CourseFinal;
import com.jungle.serviceedu.entity.vo.CourseInfoVo;
import com.jungle.serviceedu.entity.vo.CourseQuery;
import com.jungle.serviceedu.service.EduCourseService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@RequestMapping("/eduservice/course")
public class EduCourseController {
    @Autowired
    private EduCourseService courseService;

    @PostMapping("addCourseInfo")
    public R addCourseIfo(@RequestBody CourseInfoVo courseInfoVo) {
        String id = courseService.saveCourseInfo ( courseInfoVo );
        return R.ok ().data ( "courseId",id );
    }

    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfoById(@PathVariable String courseId) {
        CourseInfoVo courseInfoVo = courseService.getCourseIfoById ( courseId );
        return R.ok ().data ( "courseInfo",courseInfoVo );
    }

    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        courseService.updateCourseInfo ( courseInfoVo );
        return R.ok ();
    }

    //    查询课程确认信息
    @GetMapping("getPublishCourseInfo/{courseId}")
    public R getPublishCourseInfo(@PathVariable String courseId) {
        CourseFinal courseFinal = courseService.getPublishCourseInfo ( courseId );
        return R.ok ().data ( "courseFinal",courseFinal );
    }

    //课程的最终发布
    @ApiOperation(value = "根据id发布课程")
    @PostMapping("publish/{id}")
    public R publishCourse(@PathVariable String id) {
        courseService.publishCourse ( id );
        return R.ok ();

    }

    //课程列表
    @PostMapping("{page}/{limit}")
    public R getCourseList(@ApiParam(name = "page", value = "当前页码", required = true)
                           @PathVariable long page,
                           @ApiParam(name = "limit", value = "每页记录数", required = true)
                           @PathVariable long limit,
                           @ApiParam(name = "courseQuery", value = "查询对象")
                           @RequestBody(required = false) CourseQuery courseQuery) {
        System.out.println ( courseQuery );
        Page<EduCourse> eduCoursePage = new Page<> ( page,limit );
        courseService.getCourseList ( eduCoursePage,courseQuery );
        long total = eduCoursePage.getTotal ();
        List<EduCourse> records = eduCoursePage.getRecords ();
        return R.ok ().data ( "total",total ).data ( "rows",records );
    }

    @DeleteMapping("{courseId}")
    public R deleteCourse(@PathVariable String courseId) {
        courseService.deleteCourse ( courseId );
        return R.ok ();
    }
}

