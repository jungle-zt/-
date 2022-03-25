package com.jungle.serviceedu.controller.front;

import com.jungle.commonUtils.R;
import com.jungle.serviceedu.entity.EduCourse;
import com.jungle.serviceedu.entity.EduTeacher;
import com.jungle.serviceedu.service.EduCourseService;
import com.jungle.serviceedu.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/eduservice/indexfront")
public class IndexFrontController {
//    查询前8个热门课程，查询前四个名师
    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduTeacherService teacherService;
    @GetMapping("index")
    public R index(){
//        查询前八条热门课程
        List<EduCourse> hotCourses = courseService.getHotCourse();
//        前四个名师
        List<EduTeacher> teachers = teacherService.getFamousTeacher();
        return R.ok ().data ( "courses",hotCourses ).data ( "teachers",teachers );
    }
}
