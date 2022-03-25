package com.jungle.statistics;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling//开启定时任务
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan("com.jungle")
@MapperScan("com.jungle.statistics.mapper")
@SpringBootApplication
public class StaApplication {
    public static void main(String[] args) {
        SpringApplication.run ( StaApplication.class,args );
    }
}
