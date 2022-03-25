package com.jungle.ucenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
@ComponentScan(basePackages = {"com.jungle"})
@MapperScan("com.jungle.ucenter.mapper")
public class UCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run ( UCenterApplication.class,args );
    }
}
