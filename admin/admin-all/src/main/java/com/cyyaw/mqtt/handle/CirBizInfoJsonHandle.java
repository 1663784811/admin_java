package com.cyyaw.mqtt.handle;


import cn.hutool.json.JSONObject;
import com.cyyaw.equipment.table.entity.EqEquipment;
import com.cyyaw.mqtt.entity.CirBizConfigDev;
import com.cyyaw.mqtt.entity.CirBizConfigJson;
import com.cyyaw.mqtt.entity.CirBizInfoDev;
import com.cyyaw.mqtt.entity.CirBizInfoJson;
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
public class CirBizInfoJsonHandle extends ReceiveHandle {


    /**
     * 6.获取设备参数信息
     */
    @Override
    @RabbitListener(queues = RabbitMqMqtt.MQTT_CIRBIZINFO_JSON)
    public void handle(Message message, Channel channel, @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String routingKey, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws Exception {
        String msg = new String(message.getBody());
        JSONObject entries = new JSONObject(msg);
        log.info("【 获取设备参数信息 】{}  {}", routingKey, entries);
        JSONObject response = entries.getJSONObject("Response");
        CirBizInfoJson.PkType pkType = response.getJSONObject("PK_Type").toBean(CirBizInfoJson.PkType.class);
        JSONObject info = response.getJSONObject("Info");
        // TODO 记录日志


    }


    public void getDevCirBizInfo(String tid) {
        EqEquipment equipment = eqEquipmentService.findByTid(tid);

        // ToDo 判断设备是否在线
        String code = equipment.getCode();
        String producerCode = equipment.getProducerCode();

        // ==
        CirBizInfoDev.PkType pt = new CirBizInfoDev.PkType();
        pt.setType("CirBizInfo");
        pt.setTxnNo(createTxnNo());
        pt.setRegisterId(equipment.getTid());
        pt.setProducer(equipment.getProducerCode());
        pt.setDeviceId(code);

        // ==
        CirBizInfoDev.Values values = new CirBizInfoDev.Values();
        values.setDeviceId(code);
        // ==
        String sendStr = getRequest(pt, values);
        String rKey = "get_dev_cirBizInfo." + producerCode + "." + code;
        log.info("【 下发获取设备参数信息 】{}  {}", rKey, sendStr);
        amqpTemplate.convertAndSend(RabbitMqMqtt.MQTT_EXCHANGE, rKey, sendStr);
    }


}
