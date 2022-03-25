package com.jungle.serviceedu.controller;


import com.jungle.commonUtils.R;
import com.jungle.serviceedu.entity.subject.OneSubject;
import com.jungle.serviceedu.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author jungle
 * @since 2022-03-01
 */
@RestController
@RequestMapping("/eduservice/subject")
public class EduSubjectController {
    @Autowired
    private EduSubjectService subjectService;

    @PostMapping("addSubject")
    public R addSubject(MultipartFile file) {
        subjectService.saveSubject ( file,subjectService );
        return R.ok ();

    }
    @GetMapping("getAllSubject")
    public R getAllSubject(){
        List<OneSubject> list = subjectService.getAllOneTwoSubject ();
        return R.ok ().data ( "list",list );
    }
}

