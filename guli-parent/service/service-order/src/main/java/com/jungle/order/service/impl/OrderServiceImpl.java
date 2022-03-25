package com.jungle.order.service.impl;

import com.jungle.commonUtils.vo.CourseOrderVo;
import com.jungle.commonUtils.vo.UcenterMember;
import com.jungle.order.client.EduClient;
import com.jungle.order.client.UcenterClient;
import com.jungle.order.entity.Order;
import com.jungle.order.mapper.OrderMapper;
import com.jungle.order.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jungle.order.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author jungle
 * @since 2022-03-20
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Autowired
    private EduClient eduClient;
    @Autowired
    private UcenterClient ucenterClient;

    //生成订单
    @Override
    public String generateOrder(String courseId,String memberId) {
        CourseOrderVo orderCourseInfo = eduClient.getOrderCourseInfo ( courseId );
        UcenterMember ucenterMember = ucenterClient.getMemberById ( memberId );
        Order order = new Order ();
        order.setCourseCover ( orderCourseInfo.getCover () );//课程封面
        order.setCourseId ( courseId );
        order.setOrderNo ( OrderNoUtil.getOrderNo () );//订单号
        order.setCourseTitle ( orderCourseInfo.getTitle () );
        order.setTeacherName ( orderCourseInfo.getTeacherName () );
        order.setTotalFee ( orderCourseInfo.getPrice () );
        order.setMemberId ( memberId );
        order.setMobile ( ucenterMember.getMobile () );
        order.setNickname ( ucenterMember.getNickname () );
        order.setStatus ( 0 );
        order.setPayType ( 1 );
        baseMapper.insert ( order );
        return order.getOrderNo ();
    }
}
