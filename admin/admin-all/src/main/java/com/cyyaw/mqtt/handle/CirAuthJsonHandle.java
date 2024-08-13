package com.cyyaw.mqtt.handle;


import cn.hutool.json.JSONObject;
import com.cyyaw.equipment.table.entity.EqEquipment;
import com.cyyaw.mqtt.entity.CirAuthDev;
import com.cyyaw.mqtt.entity.CirAuthJson;
import com.cyyaw.mqtt.entity.MeasureRealtimeDev;
import com.cyyaw.mqtt.entity.MeasureRealtimeJson;
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
public class CirAuthJsonHandle extends ReceiveHandle {


    /**
     * 8.控制设置
     */
    @Override
    @RabbitListener(queues = RabbitMqMqtt.MQTT_CIRAUTH_JSON)
    public void handle(Message message, Channel channel, @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String routingKey, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws Exception {
        String msg = new String(message.getBody());
        JSONObject entries = new JSONObject(msg);
        log.info("【 控制设置 】{}  {}", routingKey, entries);
        JSONObject response = entries.getJSONObject("Response");
        CirAuthJson.PkType pkType = response.getJSONObject("PK_Type").toBean(CirAuthJson.PkType.class);
        JSONObject info = response.getJSONObject("Info");
        // TODO 记录日志


    }


    public void getCirAuth(String tid, String siteId) {
        EqEquipment equipment = eqEquipmentService.findByTid(tid);

        // ToDo 判断设备是否在线
        String code = equipment.getCode();
        String producerCode = equipment.getProducerCode();

        // ==
        CirAuthDev.PkType pt = new CirAuthDev.PkType();
        pt.setType("cirAuth");
        pt.setTxnNo(createTxnNo());
        pt.setRegisterId(equipment.getTid());
        pt.setDeviceId(code);
        pt.setProducer(equipment.getProducerCode());

        // ==
        CirAuthDev.Values values = new CirAuthDev.Values();
        values.setSiteId(siteId);

        List<CirAuthDev.CirSet> cirSetList = new ArrayList<>();


        values.setCirSet(cirSetList);

        // ==
        String sendStr = getRequest(pt, values);
        String rKey = "set_dev_cirAuth." + producerCode + "." + code;
        log.info("【 控制设置 】{}  {}", rKey, sendStr);
        amqpTemplate.convertAndSend(RabbitMqMqtt.MQTT_EXCHANGE, rKey, sendStr);
    }


}
