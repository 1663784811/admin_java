package com.cyyaw.mqtt.handle;


import cn.hutool.json.JSONObject;
import com.cyyaw.equipment.service.EqEquipmentService;
import com.cyyaw.equipment.table.entity.EqEquipment;
import com.cyyaw.mqtt.rabbit.RabbitMqMqtt;
import com.cyyaw.user.service.ChMessageService;
import com.cyyaw.user.table.entity.ChMessage;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 事件处理
 */
@Service
public class EventHandle {

    @Autowired
    private EqEquipmentService eqEquipmentService;

    @Autowired
    private ChMessageService chMessageService;

    @Autowired
    private AmqpTemplate amqpTemplate;


    /**
     * 设备上线
     * 1.通知 我的好友 在线
     * 2.发送离线消息
     */
    public void connect(String userId) {
        EqEquipment equipment = eqEquipmentService.findByCode(userId);
        if (null != equipment) {
            // 更新设备状态为在线
            equipment.setStatus(1);
            eqEquipmentService.save(equipment);
        } else {
            EqEquipment newEquipment = new EqEquipment();
            newEquipment.setNote("");
            newEquipment.setCode(userId);
            newEquipment.setName("新设备");
            newEquipment.setStatus(1);
            newEquipment.setType(0);
            eqEquipmentService.save(newEquipment);
        }
        //获取未读消息
        List<ChMessage> messageList = chMessageService.unReadMsgList(userId);
        if (null != messageList) {
            for (int i = 0; i < messageList.size(); i++) {
                ChMessage chMessage = messageList.get(i);
                //  发送消息
                String restStr = new JSONObject(chMessage).toString();
                amqpTemplate.convertAndSend(RabbitMqMqtt.MQTT_EXCHANGE, "chat." + userId, restStr);
            }
        }
    }


    /**
     * 设备下线
     */
    public void closed(String id) {
        // 通知我的好友 离线
        EqEquipment equipment = eqEquipmentService.findByCode(id);
        if (null != equipment) {
            equipment.setStatus(0);
            eqEquipmentService.save(equipment);
        }
    }

}
