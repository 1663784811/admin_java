package com.cyyaw.mqtt.handle;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.cyyaw.equipment.service.EqEquipmentService;
import com.cyyaw.equipment.service.EqModuleService;
import com.cyyaw.equipment.table.entity.EqEquipment;
import com.cyyaw.equipment.table.entity.EqModule;
import com.cyyaw.mqtt.entity.LoginDev;
import com.cyyaw.mqtt.entity.LoginDev.CirSet;
import com.cyyaw.mqtt.entity.LoginJson;
import com.cyyaw.mqtt.entity.SendAlarmDev;
import com.cyyaw.mqtt.entity.SendAlarmJson;
import com.cyyaw.mqtt.rabbit.RabbitMqMqtt;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class SendAlarmDevHandle extends ReceiveHandle {


    @Autowired
    private EqEquipmentService eqEquipmentService;

    @Autowired
    private EqModuleService eqModuleService;

    /**
     * 11.设备告警
     */
    @Override
    @RabbitListener(queues = RabbitMqMqtt.MQTT_SENDALARM_DEV)
    public void handle(Message message, Channel channel, @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String routingKey, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws Exception {
        String msg = new String(message.getBody());
        JSONObject entries = new JSONObject(msg);
        log.info("【 设备告警 】{}  {}", routingKey, entries);
        JSONObject request = entries.getJSONObject("Request");
        SendAlarmDev.PkType pkType = request.getJSONObject("PK_Type").toBean(SendAlarmDev.PkType.class);
        JSONObject valuesJson = request.getJSONObject("Info").getJSONObject("Values");
        SendAlarmDev.Values values = valuesJson.toBean(SendAlarmDev.Values.class);
        JSONArray tAlarm = valuesJson.getJSONObject("TAlarmList").getJSONArray("TAlarm");
        // ====
        String txnNo = pkType.getTxnNo();


        boolean ok = false;
        SendAlarmJson.Info info = new SendAlarmJson.Info();
        if (ok) {
            info.setResult("true");
            info.setMsg("成功");
        } else {
            info.setResult("false");
            info.setMsg("失败");
        }
        SendAlarmJson.PkType pt = new SendAlarmJson.PkType();
        pt.setType("alarm");
        pt.setTxnNo(txnNo);
        channel.basicAck(tag, false);
        String sendStr = getResponse(pt, info);
        String rKey = recoverTopic("topic_json_sendAlarm", routingKey);
        log.info("【 设备告警 】{}  {}", rKey, entries);
        amqpTemplate.convertAndSend(RabbitMqMqtt.MQTT_EXCHANGE, rKey, sendStr);
    }


}
