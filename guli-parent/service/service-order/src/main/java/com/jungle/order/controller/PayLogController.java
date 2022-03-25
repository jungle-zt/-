package com.jungle.order.controller;


import com.jungle.commonUtils.R;
import com.jungle.order.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author jungle
 * @since 2022-03-20
 */
@RestController
@RequestMapping("/eduorder/payLog")
public class PayLogController {
    @Autowired
    private PayLogService payLogService;

    @PostMapping("createNative/{orderNo}")
    public R createNative(@PathVariable String orderNo){
        Map map = payLogService.createNative(orderNo);
        return R.ok ().data ( "map",map );
    }

    @GetMapping("/queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable String orderNo) {
//调用查询接口
        Map<String, String> map = payLogService.queryPayStatus(orderNo);
        if (map == null) {//出错
            return R.error().code ( 25000 ).message("支付出错");
        }
        if (map.get("trade_state").equals("SUCCESS")) {//如果成功
//更改订单状态
            payLogService.updateOrderStatus(map);
            return R.ok().message("支付成功");
        }
        return R.ok().code(25000).message("支付中");
    }
}

