package com.cyyaw.mqtt.rabbit;


import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@Configuration
public class RabbitMqDelay {


    //延时交换机
    private static final String DELAY_EXCHANGE = "delay.exchange";

    //延时队列
    public static final String DELAY_QUEUE = "delay_queue";


    @Bean
    public Exchange delayedMessageExchange() {
        Map<String, Object> args = new HashMap<>();
        // 自定义交换机的类型
        args.put("x-delayed-type", "direct");
        return new CustomExchange(DELAY_EXCHANGE, "x-delayed-message", true, false, args);
    }


    @Bean
    public Queue delayedMessageQueue() {
        return QueueBuilder.durable(DELAY_QUEUE).build();
    }



    @Bean
    public Binding bindingDelayedMessage() {
        return BindingBuilder.bind(delayedMessageQueue()).to(delayedMessageExchange()).with("#").noargs();
    }



}
