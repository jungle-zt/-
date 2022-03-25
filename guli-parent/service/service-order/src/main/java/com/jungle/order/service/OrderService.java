package com.jungle.order.service;

import com.jungle.order.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author jungle
 * @since 2022-03-20
 */
public interface OrderService extends IService<Order> {

    String generateOrder(String courseId,String memberId);
}
