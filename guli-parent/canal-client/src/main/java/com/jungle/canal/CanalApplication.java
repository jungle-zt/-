package com.jungle.canal;

import com.jungle.canal.client.CanalClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class CanalApplication implements CommandLineRunner {
    @Resource
    private CanalClient canalClient;

    public static void main(String[] args) {
        SpringApplication.run ( CanalApplication.class,args );
    }

    @Override
    public void run(String... args) throws Exception {
        canalClient.run ();
    }
}
