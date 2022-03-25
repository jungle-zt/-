package com.jungle.serviceedu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jungle.serviceedu.entity.EduSubject;
import com.jungle.serviceedu.entity.excel.SubjectData;
import com.jungle.serviceedu.entity.subject.OneSubject;
import com.jungle.serviceedu.entity.subject.TwoSubject;
import com.jungle.serviceedu.listener.SubjectExcelListener;
import com.jungle.serviceedu.mapper.EduSubjectMapper;
import com.jungle.serviceedu.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author jungle
 * @since 2022-03-01
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject(MultipartFile file,EduSubjectService subjectService) {

        try {
            InputStream inputStream = file.getInputStream ();
            EasyExcel.read ( inputStream,SubjectData.class,new SubjectExcelListener ( subjectService ) ).sheet ().doRead ();
        } catch (IOException e) {
            e.printStackTrace ();
        }

    }

    @Override
    public List<OneSubject> getAllOneTwoSubject() {
//        查询出所有的一级分类
        QueryWrapper<EduSubject> oneWrapper = new QueryWrapper<> ();
        oneWrapper.eq ( "parent_id",0 );
//        List<EduSubject> oneSubjects = baseMapper.selectList ( oneWrapper );
        List<EduSubject> oneSubjects = this.list ( oneWrapper );

//        查询出所有的二级分类
        QueryWrapper<EduSubject> twoWrapper = new QueryWrapper<> ();
        twoWrapper.ne ( "parent_id",0 );
        List<EduSubject> twoSubjects = baseMapper.selectList ( twoWrapper );
//        封装
        ArrayList<OneSubject> finalSubjectList = new ArrayList<> ();
        for (EduSubject oneEduSubject : oneSubjects) {
            OneSubject oneSubject = new OneSubject ();
//            oneSubject.setId ( subject.getId () );
//            oneSubject.setTitle ( subject.getTitle () );
            BeanUtils.copyProperties ( oneEduSubject,oneSubject );

            finalSubjectList.add ( oneSubject );
            List<TwoSubject> children = new ArrayList<> ();
            for (EduSubject twoEduSubject : twoSubjects) {
                if (twoEduSubject.getParentId ().equals (oneEduSubject.getId ()  )){
                    TwoSubject twoSubject = new TwoSubject ();
                    BeanUtils.copyProperties ( twoEduSubject,twoSubject );
                    children.add ( twoSubject );
                }
            }
            oneSubject.setChildren ( children );
        }

        return finalSubjectList;
    }
}
