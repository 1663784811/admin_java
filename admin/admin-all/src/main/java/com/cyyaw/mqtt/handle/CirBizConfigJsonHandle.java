package com.cyyaw.mqtt.handle;

import com.google.common.collect.Lists;


import cn.hutool.json.JSONObject;
import com.cyyaw.equipment.table.entity.EqEquipment;
import com.cyyaw.mqtt.entity.CirBizConfigDev;
import com.cyyaw.mqtt.entity.CirBizConfigJson;
import com.cyyaw.mqtt.entity.DeviceTimeCheckDev;
import com.cyyaw.mqtt.entity.DeviceTimeCheckJson;
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
public class CirBizConfigJsonHandle extends ReceiveHandle {


    /**
     * 5.设置设备参数信息
     */
    @Override
    @RabbitListener(queues = RabbitMqMqtt.MQTT_CIRBIZCONFIG_JSON)
    public void handle(Message message, Channel channel, @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String routingKey, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws Exception {
        String msg = new String(message.getBody());
        JSONObject entries = new JSONObject(msg);
        log.info("【 设置设备参数信息 】{}  {}", routingKey, entries);
        JSONObject response = entries.getJSONObject("Response");
        CirBizConfigJson.PkType pkType = response.getJSONObject("PK_Type").toBean(CirBizConfigJson.PkType.class);
        JSONObject info = response.getJSONObject("Info");
        // TODO 记录日志


    }


    public void setDeviceTimeCheck(String tid) {
        EqEquipment equipment = eqEquipmentService.findByTid(tid);

        // ToDo 判断设备是否在线
        String code = equipment.getCode();
        String producerCode = equipment.getProducerCode();

        // ==
        CirBizConfigDev.PkType pt = new CirBizConfigDev.PkType();
        pt.setType("cirBizInfo");
        pt.setTxnNo(createTxnNo());
        pt.setRegisterId(equipment.getTid());
        pt.setProducer(equipment.getProducerCode());
        pt.setDeviceId(code);

        // ==
        CirBizConfigDev.Values values = new CirBizConfigDev.Values();
        values.setExemption("");
        values.setTempAuthDays("");
        values.setL1lpdv("");
        values.setL1lprv("");
        values.setDeviceTempT("");
        values.setCirInPlaceNum("");
        List<CirBizConfigDev.CirSet> cirSet = new ArrayList<>();

        values.setCirSet(cirSet);
        // ==
        String sendStr = getRequest(pt, values);
        String rKey = "set_dev_cirBizConfig." + producerCode + "." + code;
        log.info("【 下发设置设备参数信息 】{}  {}", rKey, sendStr);
        amqpTemplate.convertAndSend(RabbitMqMqtt.MQTT_EXCHANGE, rKey, sendStr);
    }


}
