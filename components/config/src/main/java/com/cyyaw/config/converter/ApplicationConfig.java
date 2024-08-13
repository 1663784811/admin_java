package com.cyyaw.config.converter;


import com.cyyaw.config.exception.CustomException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;


/**
 * 项目配置
 */
@Configuration
public class ApplicationConfig {


    //===================     异常处理
    @Bean
    public HandlerExceptionResolver customException() {
        CustomException exception = new CustomException();
        return exception;
    }

    //===================      类型转换器
    @Bean
    public DateConverter dateConverter() {
        return new DateConverter();
    }


}
