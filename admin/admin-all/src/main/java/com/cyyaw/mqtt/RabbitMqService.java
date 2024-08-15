package com.cyyaw.mqtt;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.cyyaw.mqtt.handle.ChatHandle;
import com.cyyaw.mqtt.handle.EventHandle;
import com.cyyaw.mqtt.handle.OrderHandle;
import com.cyyaw.mqtt.rabbit.RabbitMqDead;
import com.cyyaw.mqtt.rabbit.RabbitMqDelay;
import com.cyyaw.mqtt.rabbit.RabbitMqEvent;
import com.cyyaw.mqtt.rabbit.RabbitMqMqtt;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

@Slf4j
@Service
public class RabbitMqService {

    @Autowired
    private ChatHandle chatHandle;

    @Autowired
    private OrderHandle orderHandle;

    @Autowired
    private EventHandle eventHandle;

    /**
     * 接收消息
     */
    @RabbitListener(queues = RabbitMqMqtt.MQTT_QUEUE)
    public void listenSimpleQueueMessage(Message message, Channel channel) throws IOException {
        MessageProperties msp = message.getMessageProperties();
        long tag = msp.getDeliveryTag();
        String routingKey = msp.getReceivedRoutingKey();
        String data = new String(message.getBody());
        log.info("消费者接收到消息 ：{}", data);
        String[] strArr = routingKey.split("\\.");
        // =================================================
        if (strArr.length > 1 && strArr[0].equals("mqtt_service")) {
            JSONObject msgData = new JSONObject(data);
            if (strArr[1].equals("chat")) {
                // 聊天消息
                chatHandle.handle(msgData);
            } else if (strArr[1].equals("webrtc")) {
                // webrtc 消息

            } else if (strArr[1].equals("order")) {
                String userId = strArr[2];
                orderHandle.handle(userId, msgData);
            }
        }
        // =================================================
        try {
            channel.basicAck(tag, false);
        } catch (IOException e) {
            channel.basicNack(tag, false, true);
        }
    }

    /**
     * 设备事件
     */
    @RabbitListener(queues = RabbitMqEvent.EVENT_QUEUE)
    public void handleDeviceConnectedEvent(Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws Exception {
        MessageProperties messageProperties = message.getMessageProperties();
        Map<String, Object> headers = messageProperties.getHeaders();
        String receivedRoutingKey = messageProperties.getReceivedRoutingKey();
        String id = null;
        JSONArray array = new JSONArray(headers.get("client_properties"));
        if (!array.isEmpty()) {
            String str = array.getStr(0);
            String[] split = str.split(",");
            if (split.length >= 3) {
                id = split[2].replace("<<\"", "").replace("\">>}", "").replace("chat_application_", "");
            }
        }
        if (null != id && !id.contains("rabbitConnectionFactory")) {
            if ("connection.created".equals(receivedRoutingKey)) {
                eventHandle.connect(id);
            } else if ("connection.closed".equals(receivedRoutingKey)) {
                eventHandle.closed(id);
            }
        }
        System.out.println("ID : " + id);
        channel.basicAck(tag, false);
    }


    /**
     * 接收消息
     */
    @RabbitListener(queues = RabbitMqDead.DEAD_QUEUE)
    public void receiveD(Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws Exception {
        String msg = new String(message.getBody());
        log.info("【 死信队列 】：{}", msg);
        channel.basicAck(tag, false);
    }


    /**
     * 延时队列
     */
    @RabbitListener(queues = RabbitMqDelay.DELAY_QUEUE)
    public void receiveDelayedQueue(Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws Exception {
        String msg = new String(message.getBody());
        log.info("收到延时队列的消息：{}", new Date().toString(), msg);
        channel.basicAck(tag, false);
    }

}
