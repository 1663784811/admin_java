package com.cyyaw.mqtt.handle;

import cn.hutool.json.JSONObject;
import com.cyyaw.equipment.table.entity.EqEquipment;
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


@Slf4j
@Component
public class DeviceTimeCheckJsonHandle extends ReceiveHandle {


    /**
     * 4.设置设备时间
     */
    @Override
    @RabbitListener(queues = RabbitMqMqtt.MQTT_DEVICETIMECHECK_JSON)
    public void handle(Message message, Channel channel, @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String routingKey, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws Exception {
        String msg = new String(message.getBody());
        JSONObject entries = new JSONObject(msg);
        log.info("【 设置设备时间 】{}  {}", routingKey, entries);
        JSONObject response = entries.getJSONObject("Response");
        DeviceTimeCheckJson.PkType pkType = response.getJSONObject("PK_Type").toBean(DeviceTimeCheckJson.PkType.class);
        JSONObject info = response.getJSONObject("Info");

        // TODO 记录日志

    }


    public void setDeviceTimeCheck(String tid) {
        EqEquipment equipment = eqEquipmentService.findByTid(tid);


        // ToDo 判断设备是否在线

        String code = equipment.getCode();
        String producerCode = equipment.getProducerCode();

        // ==
        DeviceTimeCheckDev.PkType pt = new DeviceTimeCheckDev.PkType();
        pt.setType("deviceTime_CHECK");
        pt.setTxnNo(createTxnNo());
        pt.setRegisterId(equipment.getTid());
        pt.setDeviceId(equipment.getCode());
        pt.setProducer(equipment.getProducerCode());
        // ==
        JSONObject values = new JSONObject();
        JSONObject ti = new JSONObject();
        ti.set("Years", "");
        ti.set("Month", "");
        ti.set("Day", "");
        ti.set("Hour", "");
        ti.set("Minute", "");
        ti.set("Minute", "");
        ti.set("Second", "");
        // ==
        values.set("Time", ti);
        String sendStr = getRequest(pt, values);
        String rKey = "set_dev_deviceTimeCheck." + producerCode + "." + code;
        log.info("【 下发设置设备时间 】{}  {}", rKey, sendStr);
        amqpTemplate.convertAndSend(RabbitMqMqtt.MQTT_EXCHANGE, rKey, sendStr);
    }


}
