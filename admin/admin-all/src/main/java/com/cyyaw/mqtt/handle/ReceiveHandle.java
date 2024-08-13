package com.cyyaw.mqtt.handle;

import cn.hutool.json.JSONObject;
import com.cyyaw.equipment.service.EqEquipmentService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;

import java.util.Date;

public abstract class ReceiveHandle {


    @Autowired
    protected AmqpTemplate amqpTemplate;

    @Autowired
    protected EqEquipmentService eqEquipmentService;


    /**
     * 接收处理
     */
    public abstract void handle(Message message, Channel channel, @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String routingKey, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws Exception;


    /**
     * 生成流水号
     */
    public String createTxnNo() {
        return new Date().getTime() + "";
    }

    /**
     * 组装回复数据
     */
    public String getResponse(Object pt, Object info) {
        JSONObject rest = new JSONObject();
        JSONObject response = new JSONObject();
        response.set("PK_Type", pt);
        response.set("Info", info);
        rest.set("Response", response);
        return rest.toString();
    }

    /**
     * 组装发送数据
     */
    public String getRequest(Object pt, Object values) {
        JSONObject rest = new JSONObject();
        JSONObject response = new JSONObject();
        response.set("PK_Type", pt);
        JSONObject info = new JSONObject();
        info.set("Values", values);
        response.set("Info", info);
        rest.set("Request", response);
        return rest.toString();
    }

    /**
     * 获取回复主题
     */
    public String recoverTopic(String topic, String key) {
        int index = key.indexOf(".");
        if (index != -1) {
            return topic + key.substring(index);
        }
        return "";
    }

}
