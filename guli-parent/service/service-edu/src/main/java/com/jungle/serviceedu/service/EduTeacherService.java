package com.jungle.serviceedu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jungle.serviceedu.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author jungle
 * @since 2022-02-18
 */
public interface EduTeacherService extends IService<EduTeacher> {

    List<EduTeacher> getFamousTeacher();

    Map<String, Object> getAllTeacherPage(Page<EduTeacher> teacherPage);
}
