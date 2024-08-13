package com.cyyaw.mqtt.handle;


import com.cyyaw.mqtt.MqttService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 聊天消息
 */
@Slf4j
@Component
public class ChatMsgHandle {

    @Autowired
    private MqttService mqttService;


    public void handle(String to, String data) {
        mqttService.sendChat(to, data);
    }


}
