package com.cyyaw.mqtt.rabbit;


import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 */
@Configuration
public class RabbitMqDead {




    //死信交换机名称
    public static final String DEAD_EXCHANGE = "dead.exchange";

    //死信队列名称
    public static final String DEAD_QUEUE = "dead_queue";



    @Bean
    public Exchange deadExchange() {
        return ExchangeBuilder.topicExchange(DEAD_EXCHANGE).durable(true).build();
    }


    @Bean
    public Queue deadQueue() {
        return QueueBuilder.durable(DEAD_QUEUE).build();
    }


    @Bean
    public Binding bindingDead() {
        return BindingBuilder.bind(deadQueue()).to(deadExchange()).with("#").noargs();
    }



}
