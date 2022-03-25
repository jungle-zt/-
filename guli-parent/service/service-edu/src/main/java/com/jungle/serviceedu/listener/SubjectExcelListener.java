package com.jungle.serviceedu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellData;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jungle.serviceBase.exception.GuliException;
import com.jungle.serviceedu.entity.EduSubject;
import com.jungle.serviceedu.entity.excel.SubjectData;
import com.jungle.serviceedu.service.EduSubjectService;

import java.util.Map;

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    //    SubjectExcelListener不能交给spring管理，需要自己new，不能注入其他对象
//    不能实现数据库操作
    public EduSubjectService subjectService;

    public SubjectExcelListener() {
    }

    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    private EduSubject existOneSubject(EduSubjectService subjectService,String name) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<> ();
        wrapper.eq ( "title",name );
        wrapper.eq ( "parent_id","0" );
        EduSubject oneSubject = subjectService.getOne ( wrapper );
        return oneSubject;

    }
    private EduSubject existTwoSubject(EduSubjectService subjectService,String name,String pid) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<> ();
        wrapper.eq ( "title",name );
        wrapper.eq ( "parent_id",pid );
        EduSubject twoSubject = subjectService.getOne ( wrapper );
        return twoSubject;

    }

    @Override
    public void invoke(SubjectData subjectData,AnalysisContext analysisContext) {
        if (subjectData == null) {
            throw new GuliException ( 2001,"文件数据为空" );
        }
        EduSubject oneSubject = this.existOneSubject ( subjectService,subjectData.getOneSubjectName () );

        if(oneSubject  == null){
            oneSubject=new EduSubject ();
            oneSubject.setParentId ( "0" );
            oneSubject.setTitle ( subjectData.getOneSubjectName () );
            subjectService.save ( oneSubject );
        }

        String pid = oneSubject.getId ();
        EduSubject twoSubject = this.existTwoSubject ( subjectService,subjectData.getTwoSubjectName (),pid );
        if(twoSubject== null){
            twoSubject=new EduSubject ();
            twoSubject.setParentId ( pid );
            twoSubject.setTitle ( subjectData.getTwoSubjectName () );
            subjectService.save ( twoSubject );
        }

    }

    @Override
    public void invokeHead(Map<Integer, CellData> headMap,AnalysisContext context) {
        super.invokeHead ( headMap,context );
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
