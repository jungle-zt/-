package com.jungle.serviceedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jungle.serviceedu.entity.EduTeacher;
import com.jungle.serviceedu.mapper.EduTeacherMapper;
import com.jungle.serviceedu.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author jungle
 * @since 2022-02-18
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {
    @Cacheable(value = "teacher",key = "'famousTeacher'")
    @Override
    public List<EduTeacher> getFamousTeacher() {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<> ();
        wrapper.orderByDesc ( "id" );
        wrapper.last ( "limit 4" );

        return baseMapper.selectList ( wrapper );

    }
// 分页查询讲师
    @Override
    public Map<String, Object> getAllTeacherPage(Page<EduTeacher> teacherPage) {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<> ();
        wrapper.orderByDesc ( "id" );
        baseMapper.selectPage ( teacherPage,wrapper );
        HashMap<String, Object> pageMap = new HashMap<> ();
        pageMap.put ( "records",teacherPage.getRecords () );
        pageMap.put ("current", teacherPage.getCurrent () );
        pageMap.put ( "pages",teacherPage.getPages () );
        pageMap.put ( "size",teacherPage.getSize () );
        pageMap.put ( "total",teacherPage.getTotal () );
        pageMap.put ( "hasNext",teacherPage.hasNext () );
        pageMap.put ( "hasPrevious",teacherPage.hasPrevious () );
        return pageMap;
    }
}
