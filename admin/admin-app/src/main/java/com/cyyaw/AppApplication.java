package com.cyyaw;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.sql.Connection;

@Slf4j
@ComponentScan(basePackages = {"com.cyyaw.**"}, excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com\\.cyyaw\\.mqtt\\..*"))
@SpringBootApplication
public class AppApplication {

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext run = SpringApplication.run(AppApplication.class, args);
        DataSource ds = run.getBean(DataSource.class);
        Connection connection = ds.getConnection();
        log.info("数据库: {}", connection.getCatalog());
        log.info("------------ 启动成功 ---------");
        Environment environment = run.getBean(Environment.class);
        log.info("打开程序：http://127.0.0.1:" + environment.getProperty("local.server.port"));
        log.info("文档地址：http://127.0.0.1:" + environment.getProperty("local.server.port") + "/doc.html");
    }


}

