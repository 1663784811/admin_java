package com.cyyaw;

import cn.hutool.json.JSONUtil;
import com.cyyaw.jpa.util.DataBaseUtils;
import com.cyyaw.jpa.util.entity.FieldInfo;
import com.cyyaw.jpa.util.entity.TableInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

@Slf4j
@SpringBootApplication
public class AdminApplication {

    @Autowired
    @Qualifier("enterpriseDataSource")
    private DataSource dataSource;

    @Autowired
    @Qualifier("configDataSource")
    private DataSource dataSourceConfig;

    @Autowired
    @Qualifier("adminDataSource")
    private DataSource adminDataSource;



    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(AdminApplication.class, args);
        log.info("------------ 启动成功 ---------");


//        String eCode = "aaa";
//        LoginController bean = run.getBean(LoginController.class);
//        EnterpriseRegisterRequest b = new EnterpriseRegisterRequest();
//        EEnterprise e = new EEnterprise();
//        e.setCreateTime(new Date());
//        e.setCode(eCode);
//        e.setName("企业名称");
//        b.setEEnterprise(e);
//        LoginRequest l = new LoginRequest();
//        l.setCodeUuid("");
//        l.setCode(eCode);
//        l.setUserName("root");
//        l.setPassword("root");
//        b.setAdmin(l);
//        bean.enterpriseRegister(b);


        AdminApplication application = run.getBean(AdminApplication.class);

//        application.loadTable(application.dataSource);
//        application.loadTable(application.dataSourceConfig, "c_page");
//        application.loadTable(application.dataSourceConfig, "c_page_components");

//        application.loadTable(application.adminDataSource, "u_user");
//        application.loadTable(application.adminDataSource, "t_power");


    }


    /**
     * @param dataSource
     */
    public void loadTable(DataSource dataSource, String table) {
        System.out.println(dataSource);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<FieldInfo> store = DataBaseUtils.tableInfo(jdbcTemplate, table);
        crateData(100, dataSource, table, store);
    }


    /**
     * 生成数据
     */
    public void crateData(int numb, DataSource dataSource, String table, List<FieldInfo> fieldInfos) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        TableInfo tableInfo = new TableInfo();
        tableInfo.setTable(table);
        tableInfo.setFieldInfoList(fieldInfos);
        String insetSql = DataBaseUtils.createInsetSql(tableInfo);
        log.info("===============" + insetSql);
        for (int i = 0; i < numb; i++) {
            Object[] data = DataBaseUtils.createData(fieldInfos);
            log.info("ssssss::{}", JSONUtil.toJsonStr(data));
            jdbcTemplate.update(insetSql, data);
        }
    }


}
