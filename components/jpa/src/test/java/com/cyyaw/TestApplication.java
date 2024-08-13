//package com.cyyaw;
//import com.alibaba.fastjson.JSONArray;
//
//import com.alibaba.fastjson.JSONObject;
//import com.cyyaw.jpa.common.dao.CommonDao;
//import com.cyyaw.jpa.util.entity.CommonSaveData;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.ConfigurableApplicationContext;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//
//@Slf4j
//@EnableSwagger2
//@SpringBootApplication
//public class TestApplication {
//
//
//    public static void main(String[] args) {
//        ConfigurableApplicationContext run = SpringApplication.run(TestApplication.class, args);
//        log.info("------------ 启动成功 ---------");
//
//        CommonDao bean = run.getBean(CommonDao.class);
//
//
//        CommonSaveData commonSaveData = new CommonSaveData();
//
//        JSONArray array = new JSONArray();
//
//
//        JSONObject obj =  new JSONObject();
//
//        obj.put("del", 1);
//
//
//        array.add(obj);
//        commonSaveData.setTable("t_power");
//        commonSaveData.setData(array);
//
//        bean.save(commonSaveData);
//    }
//
//}
