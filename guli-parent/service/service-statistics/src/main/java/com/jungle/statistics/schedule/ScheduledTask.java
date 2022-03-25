package com.jungle.statistics.schedule;

import com.jungle.statistics.service.StatisticsDailyService;
import com.jungle.statistics.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduledTask {
    @Autowired
    private StatisticsDailyService statisticsDailyService;

    //    @Scheduled(cron = "0/5 * * * * ?")
//    public void task1() {
//        System.out.println ( "***********task1执行了" );
//    }
//    每天凌晨一点执行,查询统计数据并添加数据库
    @Scheduled(cron = "0 0 1 * * ? ")
    public void task() {
        statisticsDailyService.countRegister ( DateUtil.formatDate ( DateUtil.addDays ( new Date (),-1 ) ) );
    }

}
