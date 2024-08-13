package com.cyyaw.mqtt;


import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMqConfig {

    // MQTT交换机
    public static final String MQTT_EXCHANGE = "amq.topic";
    public static final String MQTT_QUEUE = "mqtt_service";


    //===
    public static final String MQTT_SERVER_EXCHANGE = "mqtt.server.exchange";

    public static final String MQTT_SERVER_QUEUE = "mqtt.server.queue";


    // ============================================      虚拟主机


    // ====================================================================================================================================
    // ====================================================================================================================================
    // ====================================================================================================================================      交换机
    // ====================================================================================================================================
    // ====================================================================================================================================

    @Bean
    public Exchange mqttExchange() {
        return ExchangeBuilder.topicExchange(MQTT_EXCHANGE).durable(true).build();
    }


    @Bean
    public Exchange mqttServerExchange() {
        return ExchangeBuilder.topicExchange(MQTT_SERVER_EXCHANGE).durable(true).build();
    }


    // =====================================================================================================================================
    // =====================================================================================================================================
    // =====================================================================================================================================      队列
    // =====================================================================================================================================
    // =====================================================================================================================================
    private Map<String, Object> getArguments() {
        Map<String, Object> arguments = new HashMap<>();
        //设置死信交换机
        arguments.put("x-dead-letter-exchange", "sss");
        //设置死信routingKey
        arguments.put("x-dead-letter-routing-key", "x.dead.letter.routing.key");
        // 设置死信时间 ( 30秒 )
        arguments.put("x-message-ttl", 10000);
        return arguments;
    }

    /**
     * mqtt
     */
    @Bean
    public Queue mqttQueue() {
        return QueueBuilder.durable(MQTT_QUEUE).withArguments(getArguments()).build();
    }

    @Bean
    public Queue mqttServerQueue() {
        return QueueBuilder.durable(MQTT_SERVER_QUEUE).withArguments(getArguments()).build();
    }

    // =================================================================================================================================
    // =================================================================================================================================
    // =================================================================================================================================      队列 绑定 交换机
    // =================================================================================================================================
    // =================================================================================================================================

    @Bean
    public Binding bindingMqtt() {
        return BindingBuilder.bind(mqttQueue()).to(mqttExchange()).with("mqtt_service.#").noargs();
    }


    @Bean
    public Binding bindingMqttServer() {
        return BindingBuilder.bind(mqttServerQueue()).to(mqttServerExchange()).with("#").noargs();
    }


    @Bean
    public Binding bindingMqttToMqttServer() {
        return BindingBuilder.bind(mqttServerExchange()).to(mqttExchange()).with("#").noargs();
    }

}
