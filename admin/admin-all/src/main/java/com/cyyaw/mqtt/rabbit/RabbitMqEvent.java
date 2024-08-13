package com.cyyaw.mqtt.rabbit;


import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 事件队列
 */

@Configuration
public class RabbitMqEvent {


    // 事件交换机
    private static final String EVENT_EXCHANGE = "amq.rabbitmq.event";
    // 事件队列
    public static final String EVENT_QUEUE = "event_queue";


    /**
     * 交换机
     */
    @Bean
    public Exchange eventExchange() {
        return ExchangeBuilder.topicExchange(EVENT_EXCHANGE).durable(true).internal().build();
    }


    /**
     * 事件队列
     */
    @Bean
    public Queue eventQueue() {
        return QueueBuilder.durable(EVENT_QUEUE).build();
    }

    /**
     * 绑定
     */
    @Bean
    public Binding bindingEvent() {
        return BindingBuilder.bind(eventQueue()).to(eventExchange()).with("connection.#").noargs();
    }

}
