package com.cyyaw.mqtt.handle;


import cn.hutool.json.JSONObject;
import com.cyyaw.equipment.table.entity.EqEquipment;
import com.cyyaw.mqtt.entity.CirAuthDev;
import com.cyyaw.mqtt.entity.CirAuthJson;
import com.cyyaw.mqtt.entity.SwChangeDev;
import com.cyyaw.mqtt.entity.SwChangeJson;
import com.cyyaw.mqtt.rabbit.RabbitMqMqtt;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class SwChangeJsonHandle extends ReceiveHandle {


    /**
     * 10.遥控
     */
    @Override
    @RabbitListener(queues = RabbitMqMqtt.MQTT_SWCHANGE_JSON)
    public void handle(Message message, Channel channel, @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String routingKey, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws Exception {
        String msg = new String(message.getBody());
        JSONObject entries = new JSONObject(msg);
        log.info("【 遥控 】{}  {}", routingKey, entries);
        JSONObject response = entries.getJSONObject("Response");
        SwChangeJson.PkType pkType = response.getJSONObject("PK_Type").toBean(SwChangeJson.PkType.class);
        JSONObject info = response.getJSONObject("Info");
        // TODO 记录日志


    }


    public void swChange(String tid) {
        EqEquipment equipment = eqEquipmentService.findByTid(tid);

        // ToDo 判断设备是否在线
        String code = equipment.getCode();
        String producerCode = equipment.getProducerCode();

        // ==
        SwChangeDev.PkType pt = new SwChangeDev.PkType();
        pt.setType("swChange");
        pt.setTxnNo(createTxnNo());
        pt.setRegisterId(equipment.getTid());
        pt.setProducer(equipment.getProducerCode());
        pt.setDeviceId(code);

        // ==
        SwChangeDev.Values values = new SwChangeDev.Values();
        values.setSiteId("");
        values.setCirNo("");
        values.setSw("");
        // ==
        String sendStr = getRequest(pt, values);
        String rKey = "set_dev_swChange." + producerCode + "." + code;
        log.info("【 遥控 】{}  {}", rKey, sendStr);
        amqpTemplate.convertAndSend(RabbitMqMqtt.MQTT_EXCHANGE, rKey, sendStr);
    }


}
