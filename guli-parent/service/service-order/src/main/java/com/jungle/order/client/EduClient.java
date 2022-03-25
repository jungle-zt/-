package com.jungle.order.client;

import com.jungle.commonUtils.vo.CourseOrderVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-edu")
public interface EduClient {
    @GetMapping("/eduservice/courseFront/getOrderCourseInfo/{courseId}")
    public CourseOrderVo getOrderCourseInfo(@PathVariable("courseId") String courseId);
}
