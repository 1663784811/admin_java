package com.cyyaw.blackroom;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@SpringBootApplication
public class BackroomApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(BackroomApplication.class, args);
        log.info("------------ 启动成功 ---------");

    }

}
