package com.jungle.serviceedu.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jungle.commonUtils.utils.JwtUtils;
import com.jungle.commonUtils.R;
import com.jungle.commonUtils.vo.CourseOrderVo;
import com.jungle.serviceedu.client.OrderClient;
import com.jungle.serviceedu.entity.EduCourse;
import com.jungle.serviceedu.entity.chapter.ChapterVo;
import com.jungle.serviceedu.entity.frontVo.CourseQueryVo;
import com.jungle.serviceedu.entity.frontVo.CourseWebVo;
import com.jungle.serviceedu.service.EduChapterService;
import com.jungle.serviceedu.service.EduCourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/courseFront")
public class CourseFrontController {

    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduChapterService chapterService;
    @Autowired
    private OrderClient orderClient;

    @PostMapping("getCourseList/{page}/{limit}")
    public R getCourseList(@PathVariable long page,
                           @PathVariable long limit,
                           @RequestBody(required = false) CourseQueryVo courseQueryVo) {
        Page<EduCourse> coursePage = new Page<> ( page,limit );
        Map<String, Object> map = courseService.getCourseFrontList ( coursePage,courseQueryVo );
        return R.ok ().data ( "map",map );
    }

    //查询课程详情的方法
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId,HttpServletRequest req) {
        CourseWebVo courseWebVo = courseService.getFrontCourseInfoById ( courseId );
//        根据课程id查询章节和小节
        List<ChapterVo> chapterVideo = chapterService.getChapterVideoById ( courseId );
//        查询课程是被购买
        boolean isBuy = orderClient.getIsBuy ( courseId,JwtUtils.getMemberIdByJwtToken ( req ) );
        return R.ok ().data ( "courseWebVo",courseWebVo ).data ( "chapterVideo",chapterVideo ).data ( "isBuy" ,isBuy);
    }

    //  根据课程id查询课程信息用于订单
    @GetMapping("getOrderCourseInfo/{courseId}")
    public CourseOrderVo getOrderCourseInfo(@PathVariable String courseId) {
        CourseWebVo courseWebVo = courseService.getFrontCourseInfoById ( courseId );
//        根据课程id查询章节和小节
        CourseOrderVo courseOrderVo = new CourseOrderVo ();
        BeanUtils.copyProperties ( courseWebVo,courseOrderVo );
        return courseOrderVo;
    }
}
