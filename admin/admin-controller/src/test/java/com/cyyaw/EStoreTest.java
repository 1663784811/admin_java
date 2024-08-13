package com.cyyaw;

import com.cyyaw.enterprise.table.dao.EStoreDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@Slf4j
@SpringBootApplication
public class EStoreTest {

    public static void main(String[] args) {

        ConfigurableApplicationContext run = SpringApplication.run(EStoreTest.class, args);
        log.info("------------ 启动成功 ---------");
        EStoreDao bean = run.getBean(EStoreDao.class);

//        bean.
        System.out.println(bean);



    }


}
