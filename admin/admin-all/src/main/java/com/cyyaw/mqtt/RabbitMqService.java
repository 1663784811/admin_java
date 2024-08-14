package com.cyyaw.mqtt;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.cyyaw.equipment.service.EqEquipmentService;
import com.cyyaw.equipment.table.entity.EqEquipment;
import com.cyyaw.mqtt.rabbit.RabbitMqDead;
import com.cyyaw.mqtt.rabbit.RabbitMqDelay;
import com.cyyaw.mqtt.rabbit.RabbitMqEvent;
import com.cyyaw.mqtt.rabbit.RabbitMqMqtt;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
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
    private AmqpTemplate amqpTemplate;

    @Autowired
    private EqEquipmentService eqEquipmentService;


    /**
     * 接收消息
     */
    @RabbitListener(queues = RabbitMqMqtt.MQTT_QUEUE)
    public void listenSimpleQueueMessage(Message message, Channel channel) throws IOException {
        MessageProperties msp = message.getMessageProperties();
        long tag = msp.getDeliveryTag();
        String routingKey = msp.getReceivedRoutingKey();
        String data = new String(message.getBody());
        System.out.println("spring 消费者接收到消息 ：【" + data + "】");
        String[] strArr = routingKey.split("\\.");
        // =================================================

        if (strArr.length > 1 && strArr[0].equals("mqtt_service")) {

            if (strArr[1].equals("chat")) {
                // 聊天消息
                String roomId = strArr[2];
                new JSONObject(data);
                // 保存聊天消息


                // 把消息转发给其它人

            } else if (strArr[1].equals("webrtc")) {
                // webrtc 消息

            } else if (strArr[1].equals("order")) {
                // 指令
                String userId = strArr[2];


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
//                    UserBean userBean = new UserBean();
//                    userBean.setUserId(id);
//                    WebRtcMsgHandle.userBeans.put(id, userBean);
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


    /**
     * 接收消息
     */
    @RabbitListener(queues = RabbitMqDead.DEAD_QUEUE)
    public void receiveD(Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws Exception {
        String msg = new String(message.getBody());
        log.info("【 死信队列 】：{}", new JSONObject(msg));
        channel.basicAck(tag, false);
    }


    /**
     * 延时队列
     */
    @RabbitListener(queues = RabbitMqDelay.DELAY_QUEUE)
    public void receiveDelayedQueue(Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws Exception {
        String msg = new String(message.getBody());
        log.info("当前时间：{},收到延时队列的消息：{}", new Date().toString(), msg);
        channel.basicAck(tag, false);
    }

}
