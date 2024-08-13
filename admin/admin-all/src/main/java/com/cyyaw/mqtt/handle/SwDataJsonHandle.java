package com.cyyaw.mqtt.handle;


import cn.hutool.json.JSONObject;
import com.cyyaw.equipment.table.entity.EqEquipment;
import com.cyyaw.mqtt.entity.CirAuthDev;
import com.cyyaw.mqtt.entity.CirAuthJson;
import com.cyyaw.mqtt.entity.SwDataDev;
import com.cyyaw.mqtt.entity.SwDataJson;
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
public class SwDataJsonHandle extends ReceiveHandle {


    /**
     * 9.获取控制参数
     */
    @Override
    @RabbitListener(queues = RabbitMqMqtt.MQTT_SWDATA_JSON)
    public void handle(Message message, Channel channel, @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String routingKey, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws Exception {
        String msg = new String(message.getBody());
        JSONObject entries = new JSONObject(msg);
        log.info("【 获取控制参数 】{}  {}", routingKey, entries);
        JSONObject response = entries.getJSONObject("Response");
        SwDataJson.PkType pkType = response.getJSONObject("PK_Type").toBean(SwDataJson.PkType.class);
        JSONObject info = response.getJSONObject("Info");
        // TODO 记录日志


    }


    public void swData(String tid) {
        EqEquipment equipment = eqEquipmentService.findByTid(tid);

        // ToDo 判断设备是否在线
        String code = equipment.getCode();
        String producerCode = equipment.getProducerCode();

        // ==
        SwDataDev.PkType pt = new SwDataDev.PkType();
        pt.setType("swData");
        pt.setTxnNo(createTxnNo());
        pt.setRegisterId(equipment.getTid());
        pt.setProducer(equipment.getProducerCode());
        pt.setDeviceId(code);
        pt.setAccountAck(equipment.getAccount());
        pt.setPwdAck(equipment.getPwd());

        // ==
        SwDataDev.Values values = new SwDataDev.Values();
        values.setDeviceId(code);

        // ==
        String sendStr = getRequest(pt, values);
        String rKey = "set_dev_swData." + producerCode + "." + code;
        log.info("【 获取控制参数 】{}  {}", rKey, sendStr);
        amqpTemplate.convertAndSend(RabbitMqMqtt.MQTT_EXCHANGE, rKey, sendStr);
    }


}
