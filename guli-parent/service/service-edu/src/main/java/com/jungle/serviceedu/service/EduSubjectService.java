package com.jungle.serviceedu.service;

import com.jungle.serviceedu.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jungle.serviceedu.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author jungle
 * @since 2022-03-01
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file,EduSubjectService subjectService);

    List<OneSubject> getAllOneTwoSubject();
}
