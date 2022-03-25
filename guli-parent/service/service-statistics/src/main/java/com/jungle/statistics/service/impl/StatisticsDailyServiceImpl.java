package com.jungle.statistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jungle.commonUtils.R;
import com.jungle.statistics.client.UcenterClient;
import com.jungle.statistics.entity.StatisticsDaily;
import com.jungle.statistics.mapper.StatisticsDailyMapper;
import com.jungle.statistics.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author jungle
 * @since 2022-03-21
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {
    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public void countRegister(String day) {
//        先删除之前添加数据
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<> ();
        wrapper.eq ( "date_calculated",day );
        baseMapper.delete ( wrapper );
//        添加新数据
        R countR = ucenterClient.getCountRegister ( day );
        int count = (int) countR.getData ().get ( "count" );
        StatisticsDaily sta = new StatisticsDaily ();
        sta.setRegisterNum ( count );
        sta.setDateCalculated ( day );
        sta.setVideoViewNum ( RandomUtils.nextInt ( 100,200 ) );
        sta.setLoginNum ( RandomUtils.nextInt ( 100,200 ) );
        sta.setCourseNum ( RandomUtils.nextInt ( 100,200 ) );

        baseMapper.insert ( sta );
    }

    @Override
    public Map<String, Object> getShowData(String type,String begin,String end) {
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<> ();
        wrapper.between ( "date_calculated",begin,end );
        wrapper.select ( "date_calculated",type );
        List<StatisticsDaily> staList = baseMapper.selectList ( wrapper );

        ArrayList<String> dateList = new ArrayList<> ();
        ArrayList<Integer> countList = new ArrayList<> ();
        for (StatisticsDaily sta : staList) {
//            封装日期
            dateList.add ( sta.getDateCalculated () );
//            封装数量
            switch (type) {
                case "login_num":
                    countList.add ( sta.getLoginNum () );
                    break;
                case "register_num":
                    countList.add ( sta.getRegisterNum () );
                    break;
                case "video_view_num":
                    countList.add ( sta.getVideoViewNum () );
                    break;
                case "course_num":
                    countList.add ( sta.getCourseNum () );
                    break;
                default:
                    break;
            }

        }
        Map<String, Object> map = new HashMap<> ();
        map.put ( "date",dateList );
        map.put ( "count",countList );
        return map;
    }
}
