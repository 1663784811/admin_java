package com.cyyaw.mqtt.handle;
import java.util.List;
import com.cyyaw.mqtt.entity.InfoJson.CirSet;

import cn.hutool.json.JSONObject;
import com.cyyaw.equipment.service.EqEquipmentService;
import com.cyyaw.equipment.table.entity.EqEquipment;
import com.cyyaw.mqtt.entity.InfoDev;
import com.cyyaw.mqtt.entity.InfoJson;
import com.cyyaw.mqtt.entity.LoginDev;
import com.cyyaw.mqtt.entity.LoginJson;
import com.cyyaw.mqtt.rabbit.RabbitMqMqtt;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class InfoJsonHandle extends ReceiveHandle {




    /**
     * MQTT_DEVINFO_JSON  获取设备信息
     */
    @Override
    @RabbitListener(queues = RabbitMqMqtt.MQTT_LOGIN_DEV)
    public void handle(Message message, Channel channel, @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String routingKey, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws Exception {
        String msg = new String(message.getBody());
        JSONObject entries = new JSONObject(msg);
        log.info("【 获取设备信息 】{}  {}", routingKey, entries);
        JSONObject response = entries.getJSONObject("Response");
        InfoJson.PkType pkType = response.getJSONObject("PK_Type").toBean(InfoJson.PkType.class);
        JSONObject info = response.getJSONObject("Info");
        String result = info.getStr("result");
        if ("true".equals(result)) {
            InfoJson.Values values = info.getJSONObject("Values").toBean(InfoJson.Values.class);
            String producer = values.getProducer();
            String deviceId = values.getDeviceId();
            String softwareVersi = values.getSoftwareVersi();
            String protocolVersion = values.getProtocolVersion();
            String iccid = values.getIccid();
            String deviceTime = values.getDeviceTime();
            String onlineState = values.getOnlineState();
            String cirInPlaceNum = values.getCirInPlaceNum();
            List<CirSet> cirSet = values.getCirSet();

        }
    }

    /**
     * 下发消息
     */
    public void getDevInfo(String tid) {
        EqEquipment equipment = eqEquipmentService.findByTid(tid);

        // ToDo 判断设备是否在线

        String code = equipment.getCode();
        String producerCode = equipment.getProducerCode();
        InfoDev.Values values = new InfoDev.Values();
        values.setDeviceId(code);
        InfoDev.PkType pt = new InfoDev.PkType();
        pt.setType("info");
        pt.setTxnNo(createTxnNo());
        pt.setRegisterId(equipment.getTid());
        pt.setProducer(equipment.getProducerCode());
        String sendStr = getRequest(pt, values);
        String rKey = "get_dev_info." + producerCode + "." + code;
        log.info("【 下发获取设备信息 】{}  {}", rKey, sendStr);
        amqpTemplate.convertAndSend(RabbitMqMqtt.MQTT_EXCHANGE, rKey, sendStr);
    }


}
