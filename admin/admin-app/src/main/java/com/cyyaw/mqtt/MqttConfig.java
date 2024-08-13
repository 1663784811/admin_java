package com.cyyaw.mqtt;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Slf4j
@Configuration
public class MqttConfig implements MqttCallback {

    String broker = "tcp://192.168.0.158:1883";
    private String clientId = "11111111111111111";


    @Bean
    public MqttClient client() throws MqttException {
        MemoryPersistence persistence = new MemoryPersistence();
        MqttClient client = new MqttClient(broker, clientId, persistence);
        // MQTT 连接选项
        MqttConnectOptions connOpts = new MqttConnectOptions();
        // 用户名
//            connOpts.setUserName("mqtt_test:mqtt_test_user");
        connOpts.setUserName("admin");
        // 密码
        connOpts.setPassword("123456".toCharArray());
        // 保留会话
        connOpts.setCleanSession(true);
        // 设置回调
        client.setCallback(this);
        // 建立连接
        client.connect(connOpts);
        // 订阅
        client.subscribe(clientId);
        System.out.println("====================       mqtt 连接成功   ===========================");
        return client;
    }


    /**
     * 连接断开
     */
    @Override
    public void connectionLost(Throwable cause) {
        log.error("连接断开:{}", clientId);
    }

    /**
     * 接收到消息
     */
    @Override
    public void messageArrived(String topic, MqttMessage message) {
        String data = new String(message.getPayload());
        log.info("接收到消息: 主题:{}  内容: {} ", topic, data);
    }

    /**
     * 当消息传递完成并且收到所有确认时调用。
     * 对于QoS 0消息，一旦消息被传递到网络进行传递，就会调用它。
     * 对于QoS 1，当收到PUBACK时调用，
     * 对于QoS 2，当收到PUBCOMP时调用。令牌将与发布消息时返回的令牌相同
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        try {
            int messageId = token.getMessageId();
            log.info("消息传递完成: {}", messageId);
        } catch (Exception e) {
            log.error("错误:{}", e.getMessage());
        }
    }
}
