package com.jungle.cms;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan("com.jungle")
@MapperScan("com.jungle.cms.mapper")
public class CmsApplication {
    public static void main(String[] args) {
        SpringApplication.run ( CmsApplication.class,args );
    }
}

