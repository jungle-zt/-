package com.jungle.serviceedu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jungle.commonUtils.R;
import com.jungle.serviceedu.entity.EduTeacher;
import com.jungle.serviceedu.entity.vo.TeacherQuery;
import com.jungle.serviceedu.service.EduTeacherService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author jungle
 * @since 2022-02-18
 */


@RestController//@controller @responseBody
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {
    //    service注入
    @Autowired
    private EduTeacherService teacherService;

    //    查所有讲师数据
    //rest风格
    @ApiOperation(value = "查询所有讲师")
    @GetMapping("getAllTeacher")
    public R findAllTeacher() {
        List<EduTeacher> list = teacherService.list ( null );
        return R.ok ().data ( "items",list );
    }

    //    逻辑删除讲师
//    借助工具测试swagger/postman
    @ApiOperation(value = "根据id删除讲师")
    @DeleteMapping("{id}")
    public R removeTeacher(@ApiParam(name = "id", value = "讲师id") @PathVariable String id) {

        boolean isDelete = teacherService.removeById ( id );
        if (isDelete) {
            return R.ok ();
        } else {
            return R.error ();
        }
    }

    //分页查询讲师
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageTeacherList(@ApiParam(name = "current", value = "当前页数")
                             @PathVariable long current,
                             @ApiParam(name = "limit", value = "每页记录数")
                             @PathVariable long limit) {
        Page<EduTeacher> teacherPage = new Page<> ( current,limit );
        teacherService.page ( teacherPage,null );
        long total = teacherPage.getTotal ();
        List<EduTeacher> records = teacherPage.getRecords ();
        return R.ok ().data ( "total",total ).data ( "rows",records );
    }

    //条件查询带分页的方法
    @PostMapping ("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@ApiParam(name = "current", value = "当前页数")
                                  @PathVariable long current,
                                  @ApiParam(name = "limit", value = "每页记录数")
                                  @PathVariable long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery) {
        Page<EduTeacher> teacherPage = new Page<> (current,limit);
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<> ();


        String name = teacherQuery.getName ();
        Integer level = teacherQuery.getLevel ();
        String begin = teacherQuery.getBegin ();
        String end = teacherQuery.getEnd ();
        if(!StringUtils.isEmpty ( name )){
            wrapper.like ( "name",name );
        }
        if(!StringUtils.isEmpty ( level )){
            wrapper.like ( "level",level );
        }
        if(!StringUtils.isEmpty ( begin )){
            wrapper.ge ( "gmt_create",begin );
        }
        if(!StringUtils.isEmpty ( end )){
            wrapper.le ( "gmt_create",end );
        }
        teacherService.page ( teacherPage,wrapper );
        long total = teacherPage.getTotal ();
        List<EduTeacher> records = teacherPage.getRecords ();
        return R.ok ().data ( "total",total ).data ( "rows",records );
    }
//    添加讲师
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher teacher){
        boolean save = teacherService.save ( teacher );
        if(save){
            return R.ok ();
        }
        return R.error ();
    }

    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping("{id}")
    public R getById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id){
        EduTeacher teacher = teacherService.getById(id);
        return R.ok().data("item", teacher);
    }

    @ApiOperation(value = "根据ID修改讲师")
    @PostMapping("updateTeacher")
    public R updateById(
            @RequestBody EduTeacher teacher){
        boolean flag = teacherService.updateById ( teacher );
        if(flag)return R.ok ();
        return R.error ();

    }
}

