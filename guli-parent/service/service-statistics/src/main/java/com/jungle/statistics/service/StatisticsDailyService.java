package com.jungle.statistics.service;

import com.jungle.statistics.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author jungle
 * @since 2022-03-21
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    void countRegister(String day);

    Map<String, Object> getShowData(String type,String begin,String end);
}
