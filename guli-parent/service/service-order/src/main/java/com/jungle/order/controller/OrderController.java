package com.jungle.order.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jungle.commonUtils.utils.JwtUtils;
import com.jungle.commonUtils.R;
import com.jungle.order.entity.Order;
import com.jungle.order.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author jungle
 * @since 2022-03-20
 */
@RestController
@RequestMapping("/eduorder/order")
public class OrderController {

    @Autowired
    private OrderService orderService;


    //   1.生成订单的接口
    @ApiOperation(value = "生成订单的接口")
    @PostMapping("generateOrder/{courseId}")
    public R generateOrder(@PathVariable String courseId,HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken ( request );
        String orderNo = orderService.generateOrder ( courseId,memberId );
        return R.ok ().data ( "orderNo",orderNo );
    }
//    2.查询订单信息
    @ApiOperation(value = "根据订单号查询订单信息")
    @GetMapping ("{orderNo}")
    public R getOrderInfoByNo(@PathVariable String orderNo){
        QueryWrapper<Order> wrapper = new QueryWrapper<> ();
        wrapper.eq ( "order_no",orderNo );
        Order order = orderService.getOne ( wrapper );
        return R.ok ().data ( "order",order );
    }
//    3.生成微信支付的二维码

//    4.查询订单支付状态
//    查询是否已经购买
    @GetMapping("getIsBuy/{courseId}/{memberId}")
    public boolean getIsBuy(@PathVariable String courseId,@PathVariable String memberId){
        QueryWrapper<Order> wrapper = new QueryWrapper<> ();
        wrapper.eq ( "course_id",courseId );
        wrapper.eq ( "status",1 );
        wrapper.eq ( "member_id",memberId );
        int count = orderService.count ( wrapper );
        return count != 0;
    }
}

