package com.cyyaw.mqtt;

import cn.hutool.json.JSONArray;
import com.cyyaw.equipment.service.EqEquipmentService;
import com.cyyaw.equipment.table.entity.EqEquipment;
import com.cyyaw.mqtt.handle.ChatMsgHandle;
import com.cyyaw.mqtt.handle.UserBean;
import com.cyyaw.mqtt.handle.WebRtcMsgHandle;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Service
public class RabbitMQService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private ChatMsgHandle chatMsgHandle;

    @Autowired
    private WebRtcMsgHandle webRtcMsgHandle;

    @Autowired
    private MqttService mqttService;


    @Autowired
    private EqEquipmentService eqEquipmentService;

    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend("ssssssssss", message);
    }

    public String receiveMessage() {
        return (String) rabbitTemplate.receiveAndConvert("myQueue");
    }


    @RabbitListener(queues = RabbitMqConfig.MQTT_QUEUE)
    public void listenSimpleQueueMessage(Message message, Channel channel) throws IOException {
        MessageProperties msp = message.getMessageProperties();
        long tag = msp.getDeliveryTag();
        String routingKey = msp.getReceivedRoutingKey();
        String data = new String(message.getBody());
        System.out.println("spring 消费者接收到消息 ：【" + data + "】");

        String[] strArr = routingKey.split("\\.");
        if (strArr.length > 0 && strArr[0].equals("mqtt_service")) {
            if (strArr.length > 1 && strArr[1].equals("chat")) {
                String to = routingKey.replace("mqtt_service.chat.", "");
                chatMsgHandle.handle(to, data);
            } else if (strArr.length > 1 && strArr[1].equals("webrtc")) {
                String to = routingKey.replace("mqtt_service.webrtc.", "");
                webRtcMsgHandle.handle(to, data);
            } else {
                String topic = routingKey.replace("mqtt_service.", "");
                mqttService.send(topic, data);
            }
        }
        try {
            channel.basicAck(tag, false);
        } catch (IOException e) {
            channel.basicNack(tag, false, true);
        }
    }

    @RabbitListener(queues = "event_queue")
    public void handleDeviceConnectedEvent(Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws Exception {
        MessageProperties messageProperties = message.getMessageProperties();
        Map<String, Object> headers = messageProperties.getHeaders();
        String receivedRoutingKey = messageProperties.getReceivedRoutingKey();

        String id = null;
        JSONArray array = new JSONArray(headers.get("client_properties"));
        if (null != array && array.size() > 0) {
            String str = array.getStr(0);
            str.toString();
            String[] split = str.split(",");
            if (split.length >= 3) {
                id = split[2].replace("<<\"", "").replace("\">>}", "").replace("chat_application_", "");
            }
        }
        if (null != id) {
            if (id.indexOf("rabbitConnectionFactory") == -1) {
                if ("connection.created".equals(receivedRoutingKey)) {
                    messageProperties.getHeader("queue");
                    // 通知 我的好友 在线
                    UserBean userBean = new UserBean();
                    userBean.setUserId(id);
                    WebRtcMsgHandle.userBeans.put(id, userBean);
                    System.out.println("设备上线 event: " + receivedRoutingKey + "   " + headers);

                    EqEquipment equipment = eqEquipmentService.findByCode(id);
                    if (null != equipment) {
                        // 更新设备状态为在线
                        equipment.setStatus(1);
                        eqEquipmentService.save(equipment);
                    } else {
                        EqEquipment newEquipment = new EqEquipment();
                        newEquipment.setNote("");
                        newEquipment.setCode(id);
                        newEquipment.setName("新设备");
                        newEquipment.setStatus(1);
                        newEquipment.setType(0);
                        eqEquipmentService.save(newEquipment);
                    }

                } else if ("connection.closed".equals(receivedRoutingKey)) {
                    messageProperties.getHeader("queue");
                    // 通知我的好友 离线
                    System.out.println("设备下线 event: " + receivedRoutingKey + "   " + headers);
//                    WebRtcMsgHandle.userBeans.remove(id);
                    EqEquipment equipment = eqEquipmentService.findByCode(id);
                    if (null != equipment) {
                        equipment.setStatus(0);
                        eqEquipmentService.save(equipment);
                    }
                }
            }
        }
        System.out.println("ID : " + id);
        if ("".equals("")) {
            System.out.println("业务正常处理，确认消息");
            channel.basicAck(tag, false);
        } else {
            System.out.println("业务处理失败，拒绝接收消息");
            channel.basicNack(tag, false, true);
        }
    }

}
