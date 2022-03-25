package com.jungle.order.service;

import com.jungle.order.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author jungle
 * @since 2022-03-20
 */
public interface PayLogService extends IService<PayLog> {

    void updateOrderStatus(Map<String, String> map) ;

    Map<String, String> queryPayStatus(String orderNo);

    Map createNative(String orderNo);
}
