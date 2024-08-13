package com.cyyaw.config.datasource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean(name = "enterpriseDataSource")
    @Qualifier("enterpriseDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.enterprise")
    public DataSource enterprise() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "adminDataSource")
    @Qualifier("adminDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.admin")
    public DataSource admin() {
        return DataSourceBuilder.create().build();
    }



    @Bean(name = "storeDataSource")
    @Qualifier("storeDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.store")
    public DataSource store() {
        return DataSourceBuilder.create().build();
    }


    @Primary
    @Bean(name = "configDataSource")
    @Qualifier("configDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.config")
    public DataSource config() {
        return DataSourceBuilder.create().build();
    }



    @Bean(name = "spiderDataSource")
    @Qualifier("spiderDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.spider")
    public DataSource spider() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "webDataSource")
    @Qualifier("webDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.web")
    public DataSource web() {
        return DataSourceBuilder.create().build();
    }



}
