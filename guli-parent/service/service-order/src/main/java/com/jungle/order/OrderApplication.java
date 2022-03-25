package com.jungle.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.jungle")
@EnableFeignClients
@EnableDiscoveryClient
@MapperScan("com.jungle.order.mapper")
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run ( OrderApplication.class,args );
    }

}
