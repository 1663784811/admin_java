package com.cyyaw.mqtt.rabbit;


import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMqMqtt {

    // MQTT交换机
    public static final String MQTT_EXCHANGE = "amq.topic";
    public static final String MQTT_QUEUE = "mqtt_service";


    //===
    public static final String MQTT_SERVER_EXCHANGE = "mqtt.server.exchange";

    // ========================================================================================================================


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
        arguments.put("x-dead-letter-exchange", RabbitMqDead.DEAD_EXCHANGE);
        //设置死信routingKey
        arguments.put("x-dead-letter-routing-key", "x.dead.letter.routing.key");
        // 设置死信时间 ( 120秒 )
        arguments.put("x-message-ttl", 1000 * 10);
        return arguments;
    }

    /**
     * mqtt
     */
    @Bean
    public Queue mqttQueue() {
        return QueueBuilder.durable(MQTT_QUEUE).withArguments(getArguments()).build();
    }


    // =================================================================================================================================
    // =================================================================================================================================
    // =================================================================================================================================      队列 绑定 交换机
    // =================================================================================================================================
    // =================================================================================================================================

    @Bean
    public Binding bindingMqtt() {
        return BindingBuilder.bind(mqttQueue()).to(mqttServerExchange()).with(MQTT_QUEUE + ".#").noargs();
    }

    @Bean
    public Binding bindingMqttToMqttServer() {
        return BindingBuilder.bind(mqttServerExchange()).to(mqttExchange()).with(MQTT_QUEUE + ".#").noargs();
    }

}
