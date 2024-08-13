package com.cyyaw.mqtt.handle;


import cn.hutool.json.JSONObject;
import com.cyyaw.equipment.table.entity.EqEquipment;
import com.cyyaw.mqtt.entity.CirBizInfoDev;
import com.cyyaw.mqtt.entity.CirBizInfoJson;
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

@Slf4j
@Component
public class MeasureRealtimeJsonHandle extends ReceiveHandle {


    /**
     * 7.获取计量实时数据
     */
    @Override
    @RabbitListener(queues = RabbitMqMqtt.MQTT_MEASUREREALTIME_JSON)
    public void handle(Message message, Channel channel, @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String routingKey, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws Exception {
        String msg = new String(message.getBody());
        JSONObject entries = new JSONObject(msg);
        log.info("【 获取计量实时数据 】{}  {}", routingKey, entries);
        JSONObject response = entries.getJSONObject("Response");
        MeasureRealtimeJson.PkType pkType = response.getJSONObject("PK_Type").toBean(MeasureRealtimeJson.PkType.class);
        JSONObject info = response.getJSONObject("Info");
        // TODO 记录日志


    }


    public void getMeasureRealtime(String tid) {
        EqEquipment equipment = eqEquipmentService.findByTid(tid);

        // ToDo 判断设备是否在线
        String code = equipment.getCode();
        String producerCode = equipment.getProducerCode();

        // ==
        MeasureRealtimeDev.PkType pt = new MeasureRealtimeDev.PkType();
        pt.setType("measureRealtime");
        pt.setTxnNo(createTxnNo());
        pt.setRegisterId(equipment.getTid());
        pt.setDeviceId(code);

        // ==
        MeasureRealtimeDev.Values values = new MeasureRealtimeDev.Values();
        values.setDeviceId(code);
        // ==
        String sendStr = getRequest(pt, values);
        String rKey = "get_dev_measureRealtime." + producerCode + "." + code;
        log.info("【 获取计量实时数据 】{}  {}", rKey, sendStr);
        amqpTemplate.convertAndSend(RabbitMqMqtt.MQTT_EXCHANGE, rKey, sendStr);
    }


}
