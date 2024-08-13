package com.cyyaw.springutils;

import com.cyyaw.mqtt.RabbitMqService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Configuration
public class CustomBeanRegistrar implements ApplicationContextAware {

    private GenericApplicationContext applicationContext;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (applicationContext instanceof GenericApplicationContext) {
            this.applicationContext = (GenericApplicationContext) applicationContext;
        }
    }


    /**
     *
     */
    @Async
    public void scanAndRegisterBeans(String packageName) {
        // 创建一个扫描器，用于扫描指定包内的 Bean
        log.info(" ==============  扫描包 : {}", packageName);
        List<Class<?>> classList = new ArrayList<>();
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(applicationContext, false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(Component.class));
        // 扫描指定包
        for (BeanDefinition bd : scanner.findCandidateComponents(packageName)) {
            String beanName = bd.getBeanClassName();
            try {
                Class<?> clazz = Class.forName(beanName);
                classList.add(clazz);
                applicationContext.registerBean(beanName, clazz);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            log.info("注册bean : {}", beanName);
        }
        /**
         *  实例化
         */
        for (int i = 0; i < classList.size(); i++) {
            applicationContext.getBean(classList.get(i));
        }
    }

}
