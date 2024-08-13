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
    /**
     * 1.申请账号
     */
    public static final String MQTT_PWD_DEV = "get_dev_pwd";

    /**
     * 2.设备注册
     */
    public static final String MQTT_LOGIN_DEV = "get_dev_login";

    /**
     * 3.获取设备信息
     */
    public static final String MQTT_DEVINFO_JSON = "topic_json_devInfo";
    /**
     * 4.设置设备时间
     */
    public static final String MQTT_DEVICETIMECHECK_JSON = "topic_json_devicetimeCheck";

    /**
     * 5.设置设备参数信息
     */
    public static final String MQTT_CIRBIZCONFIG_JSON = "topic_json_cirBizConfig";

    /**
     * 6.获取设备参数信息
     */
    public static final String MQTT_CIRBIZINFO_JSON = "topic_json_cirBizInfo";

    /**
     * 7.获取计量实时数据
     */
    public static final String MQTT_MEASUREREALTIME_JSON = "topic_json_measureRealtime";

    /**
     * 8. 控制设置
     */
    public static final String MQTT_CIRAUTH_JSON = "topic_json_cirAuth";

    /**
     * 9. 获取控制参数
     */
    public static final String MQTT_SWDATA_JSON = "topic_json_swData";


    /**
     * 10. 遥控
     */
    public static final String MQTT_SWCHANGE_JSON = "topic_json_swChange";

    /**
     * 11. 设备告警
     */
    public static final String MQTT_SENDALARM_DEV = "get_dev_sendAlarm";


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


    // ===================

    @Bean
    public Queue mqttPWDDEVQueue() {
        return QueueBuilder.durable(MQTT_PWD_DEV).withArguments(getArguments()).build();
    }

    @Bean
    public Queue mqttLOGINDevQueue() {
        return QueueBuilder.durable(MQTT_LOGIN_DEV).withArguments(getArguments()).build();
    }

    @Bean
    public Queue mqttDEVINFOJSONQueue() {
        return QueueBuilder.durable(MQTT_DEVINFO_JSON).withArguments(getArguments()).build();
    }

    @Bean
    public Queue mqttDEVICETIMECHECKJSONQueue() {
        return QueueBuilder.durable(MQTT_DEVICETIMECHECK_JSON).withArguments(getArguments()).build();
    }

    @Bean
    public Queue mqttCIRBIZCONFIGJSONQueue() {
        return QueueBuilder.durable(MQTT_CIRBIZCONFIG_JSON).withArguments(getArguments()).build();
    }

    @Bean
    public Queue mqttCIRBIZINFOJSONQueue() {
        return QueueBuilder.durable(MQTT_CIRBIZINFO_JSON).withArguments(getArguments()).build();
    }

    @Bean
    public Queue mqttMEASUREREALTIMEJSONQueue() {
        return QueueBuilder.durable(MQTT_MEASUREREALTIME_JSON).withArguments(getArguments()).build();
    }

    @Bean
    public Queue mqttCIRAUTHJSONQueue() {
        return QueueBuilder.durable(MQTT_CIRAUTH_JSON).withArguments(getArguments()).build();
    }

    @Bean
    public Queue mqttSWDATAJSONQueue() {
        return QueueBuilder.durable(MQTT_SWDATA_JSON).withArguments(getArguments()).build();
    }

    @Bean
    public Queue mqttSWCHANGEJSONQueue() {
        return QueueBuilder.durable(MQTT_SWCHANGE_JSON).withArguments(getArguments()).build();
    }

    @Bean
    public Queue mqttSENDALARMDEVQueue() {
        return QueueBuilder.durable(MQTT_SENDALARM_DEV).withArguments(getArguments()).build();
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
    public Binding bindingMqttToMqttServer() {
        return BindingBuilder.bind(mqttServerExchange()).to(mqttExchange()).with("#").noargs();
    }

    // ===========
    @Bean
    public Binding bindingPwdDev() {
        return BindingBuilder.bind(mqttPWDDEVQueue()).to(mqttServerExchange()).with(MQTT_PWD_DEV + ".#").noargs();
    }

    @Bean
    public Binding bindingLOGINDev() {
        return BindingBuilder.bind(mqttLOGINDevQueue()).to(mqttServerExchange()).with(MQTT_LOGIN_DEV + ".#").noargs();
    }

    @Bean
    public Binding bindingDEVINFOJSON() {
        return BindingBuilder.bind(mqttDEVINFOJSONQueue()).to(mqttServerExchange()).with(MQTT_DEVINFO_JSON + ".#").noargs();
    }

    @Bean
    public Binding bindingDEVICETIMECHECKJSON() {
        return BindingBuilder.bind(mqttDEVICETIMECHECKJSONQueue()).to(mqttServerExchange()).with(MQTT_DEVICETIMECHECK_JSON + ".#").noargs();
    }

    @Bean
    public Binding bindingCIRBIZCONFIGJSON() {
        return BindingBuilder.bind(mqttCIRBIZCONFIGJSONQueue()).to(mqttServerExchange()).with(MQTT_CIRBIZCONFIG_JSON + ".#").noargs();
    }

    @Bean
    public Binding bindingCIRBIZINFOJSON() {
        return BindingBuilder.bind(mqttCIRBIZINFOJSONQueue()).to(mqttServerExchange()).with(MQTT_CIRBIZINFO_JSON + ".#").noargs();
    }

    @Bean
    public Binding bindingMEASUREREALTIMEJSON() {
        return BindingBuilder.bind(mqttMEASUREREALTIMEJSONQueue()).to(mqttServerExchange()).with(MQTT_MEASUREREALTIME_JSON + ".#").noargs();
    }

    @Bean
    public Binding bindingCIRAUTHJSON() {
        return BindingBuilder.bind(mqttCIRAUTHJSONQueue()).to(mqttServerExchange()).with(MQTT_CIRAUTH_JSON + ".#").noargs();
    }

    @Bean
    public Binding bindingSWDATAJSON() {
        return BindingBuilder.bind(mqttSWDATAJSONQueue()).to(mqttServerExchange()).with(MQTT_SWDATA_JSON + ".#").noargs();
    }

    @Bean
    public Binding bindingSWCHANGEJSON() {
        return BindingBuilder.bind(mqttSWCHANGEJSONQueue()).to(mqttServerExchange()).with(MQTT_SWCHANGE_JSON + ".#").noargs();
    }

    @Bean
    public Binding bindingSENDALARMDEV() {
        return BindingBuilder.bind(mqttSENDALARMDEVQueue()).to(mqttServerExchange()).with(MQTT_SENDALARM_DEV + ".#").noargs();
    }

}
