package com.cyyaw.springutils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class AmqpStarter implements ApplicationRunner {

    @Autowired
    private CustomBeanRegistrar customBeanRegistrar;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        log.info(" ============== 开始 rabbiMq 初始化");
//        customBeanRegistrar.scanAndRegisterBeans("com.cyyaw.mqtt.**");
    }

}
