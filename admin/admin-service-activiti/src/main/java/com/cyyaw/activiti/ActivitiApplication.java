//package com.cyyaw.activiti;
//
//
//import lombok.extern.slf4j.Slf4j;
//import org.activiti.engine.ProcessEngine;
//import org.activiti.engine.ProcessEngineConfiguration;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.web.server.WebServer;
//import org.springframework.context.ConfigurableApplicationContext;
//import org.springframework.core.env.Environment;
//
//@Slf4j
//@SpringBootApplication
//public class ActivitiApplication {
//
//
//    public static void main(String[] args) {
//        ConfigurableApplicationContext run = SpringApplication.run(ActivitiApplication.class, args);
//        Environment environment = run.getBean(Environment.class);
//        log.info("打开程序：http://127.0.0.1:" + environment.getProperty("local.server.port"));
//    }
//
//}
