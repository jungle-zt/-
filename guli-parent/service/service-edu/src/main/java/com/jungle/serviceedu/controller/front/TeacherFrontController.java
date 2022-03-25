package com.jungle.serviceedu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jungle.commonUtils.R;
import com.jungle.serviceedu.entity.EduCourse;
import com.jungle.serviceedu.entity.EduTeacher;
import com.jungle.serviceedu.service.EduCourseService;
import com.jungle.serviceedu.service.EduTeacherService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/teacherFront")
public class TeacherFrontController {
    @Autowired
    private EduTeacherService teacherService;
    @Autowired
    private EduCourseService courseService;

    //    分页查询讲师
    @ApiOperation(value = "分页查询讲师")
    @PostMapping("getAllTeacherPage/{page}/{limit}")
    public R getAllTeacherPage(@PathVariable long page,@PathVariable long limit) {
        Page<EduTeacher> teacherPage = new Page<> ( page,limit );
        Map<String, Object> map = teacherService.getAllTeacherPage ( teacherPage );
        return R.ok ().data ( "teacherMap",map );

    }
    @ApiOperation(value = "根据id查询讲师信息")
    @GetMapping("{teacherId}")
    public R getTeacherById(@PathVariable String teacherId){
        EduTeacher teacher = teacherService.getById ( teacherId );
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<> ();
        wrapper.eq ( "teacher_id",teacherId );
        List<EduCourse> list = courseService.list ( wrapper );
        return R.ok ().data ( "teacher",teacher ).data ( "courseList",list );
    }
}
