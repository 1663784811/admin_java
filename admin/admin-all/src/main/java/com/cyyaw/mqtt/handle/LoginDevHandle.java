package com.cyyaw.mqtt.handle;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.cyyaw.equipment.service.EqEquipmentService;
import com.cyyaw.equipment.service.EqModuleService;
import com.cyyaw.equipment.table.entity.EqEquipment;
import com.cyyaw.equipment.table.entity.EqModule;
import com.cyyaw.mqtt.entity.LoginDev;
import com.cyyaw.mqtt.entity.LoginDev.CirSet;
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

import java.util.List;

@Slf4j
@Component
public class LoginDevHandle extends ReceiveHandle {


    @Autowired
    private EqEquipmentService eqEquipmentService;

    @Autowired
    private EqModuleService eqModuleService;

    /**
     * 设备注册
     */
    @Override
    @RabbitListener(queues = RabbitMqMqtt.MQTT_LOGIN_DEV)
    public void handle(Message message, Channel channel, @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String routingKey, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws Exception {
        String msg = new String(message.getBody());
        JSONObject entries = new JSONObject(msg);
        log.info("【 设备注册 】{}  {}", routingKey, entries);
        JSONObject request = entries.getJSONObject("Request");
        LoginDev.PkType pkType = request.getJSONObject("PK_Type").toBean(LoginDev.PkType.class);

        LoginDev.Values values = request.getJSONObject("Info").getJSONObject("Values").toBean(LoginDev.Values.class);
        String type = pkType.getType();
        String txnNo = pkType.getTxnNo();
        String accountAck = pkType.getAccountAck();
        String pwdAck = pkType.getPwdAck();
        String registerId = pkType.getRegisterId();
        String producer = pkType.getProducer();
        String deviceId = pkType.getDeviceId();
        String deviceAssetId = pkType.getDeviceAssetId();

        String softwareVersion = values.getSoftwareVersion();
        String protocolVersion = values.getProtocolVersion();
        String cirInPlaceNum = values.getCirInPlaceNum();
        List<CirSet> cirSet = values.getCirSet();
        boolean ok = false;
        EqEquipment equipment = eqEquipmentService.findByTid(registerId);
        if (null != equipment) {
            // 判断是否已经绑定设备了
            if (StrUtil.isBlank(equipment.getCode())) {
                // 验证账号密码
                String account = equipment.getAccount();
                String pwd = equipment.getPwd();
                if (accountAck.equals(account) && pwdAck.equals(pwd)) {
                    // 绑定
                    equipment.setCode(deviceId);
                    equipment.setProducerCode(producer);
                    equipment.setDeviceAssetCode(deviceAssetId);
                    equipment.setSoftwareVersion(softwareVersion);
                    equipment.setProtocolVersion(protocolVersion);
                    equipment.setCirInPlaceNum(cirInPlaceNum);
                    eqEquipmentService.save(equipment);
                    //
                    if (null != cirSet) {
                        for (int i = 0; i < cirSet.size(); i++) {
                            CirSet cs = cirSet.get(i);
                            EqModule eqModule = new EqModule();
                            eqModule.setEnterpriseCode(equipment.getEnterpriseCode());
                            eqModule.setAppId(equipment.getAppId());
                            eqModule.setEquipmentId(equipment.getTid());
                            eqModule.setCode(cs.getCirNo());
                            eqModule.setType(cs.getCirType());
                            eqModule.setCapacity(cs.getCirCapacity());
                            eqModuleService.save(eqModule);
                        }
                    }
                    ok = true;
                }
            }
        }
        LoginJson.Info info = new LoginJson.Info();
        if (ok) {
            info.setResult("true");
            info.setMsg("注册成功");
        } else {
            info.setResult("false");
            info.setMsg("注册失败");
        }
        LoginJson.PkType pt = new LoginJson.PkType();
        pt.setType("login");
        pt.setTxnNo(txnNo);
        channel.basicAck(tag, false);
        String sendStr = getResponse(pt, info);
        String rKey = recoverTopic("topic_json_login", routingKey);
        log.info("【 设备注册 】{}  {}", rKey, entries);
        amqpTemplate.convertAndSend(RabbitMqMqtt.MQTT_EXCHANGE, rKey, sendStr);

    }


}
