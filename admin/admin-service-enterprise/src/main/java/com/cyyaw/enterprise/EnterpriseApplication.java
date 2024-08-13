package com.cyyaw.enterprise;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;



@Slf4j
@SpringBootApplication
public class EnterpriseApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(EnterpriseApplication.class, args);
        log.info("------------ 启动成功 ---------");

    }


}
