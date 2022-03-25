package com.jungle.serviceedu.controller.front;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jungle.commonUtils.utils.JwtUtils;
import com.jungle.commonUtils.R;
import com.jungle.commonUtils.vo.UcenterMember;
import com.jungle.serviceedu.client.UcenterClient;
import com.jungle.serviceedu.entity.EduComment;
import com.jungle.serviceedu.service.EduCommentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author jungle
 * @since 2022-03-18
 */
@RestController
@RequestMapping("/eduservice/comment")
public class EduCommentController {
    @Autowired
    private EduCommentService commentService;
    @Autowired
    private UcenterClient ucenterClient;

    @ApiOperation(value = "评论分页列表查询根据课程id")
    @GetMapping("{page}/{limit}")
    public R getCommentPages(@PathVariable long page,@PathVariable long limit,String courseId) {
        Page<EduComment> pageParam = new Page<> ( page,limit );
        QueryWrapper<EduComment> wrapper = new QueryWrapper<> ();
        wrapper.eq ( "course_id",courseId );
        wrapper.orderByDesc ( "gmt_create" );
        commentService.page ( pageParam,wrapper );
        List<EduComment> commentList = pageParam.getRecords ();
        Map<String, Object> map = new HashMap<> ();
        map.put ( "items",commentList );
        map.put ( "current",pageParam.getCurrent () );
        map.put ( "pages",pageParam.getPages () );
        map.put ( "size",pageParam.getSize () );
        map.put ( "total",pageParam.getTotal () );
        map.put ( "hasNext",pageParam.hasNext () );
        map.put ( "hasPrevious",pageParam.hasPrevious () );
        return R.ok ().data ( "map",map );
    }

    @ApiOperation("添加评论")
    @PostMapping("save")
    public R saveComment(@RequestBody EduComment comment,HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken ( request );
        if (StringUtils.isEmpty ( memberId )) {
            return R.error ().code ( 28004 ).message ( "请登录" );
        }
//        添加评论的用户信息
        comment.setMemberId ( memberId );
        UcenterMember ucenterMember = ucenterClient.getMemberById ( memberId );
        comment.setAvatar ( ucenterMember.getAvatar () );
        comment.setNickname ( ucenterMember.getNickname () );
        commentService.save ( comment );
        return R.ok ();
    }
}

