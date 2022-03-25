package com.jungle.serviceedu.mapper;

import com.jungle.serviceedu.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jungle.serviceedu.entity.frontVo.CourseWebVo;
import com.jungle.serviceedu.entity.vo.CourseFinal;
import com.jungle.serviceedu.entity.vo.CourseInfoVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author jungle
 * @since 2022-03-02
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    CourseFinal selectCoursePublishVoById(String id);

    CourseWebVo getFrontCourseInfoById(String courseId);
}
