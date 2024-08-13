package com.cyyaw.mqtt;


import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class MqttService {


    @Autowired
    private MqttClient mqttClient;

    public void sendWebrtc(String userId, String data) {
        send("webrtc." + userId, data);
    }


    public void sendChat(String userId, String data) {
        send("chat." + userId, data);
    }


    public void send(String topic, String data) {
        MqttMessage message = new MqttMessage();
        message.setQos(1);
        message.setPayload(data.getBytes());
        try {
            mqttClient.publish(topic, message);
        } catch (Exception e) {
            log.error("错误: 主题:{}  , 错误原因:{} ", topic, e.getMessage());
            e.printStackTrace();
        }
    }
}
