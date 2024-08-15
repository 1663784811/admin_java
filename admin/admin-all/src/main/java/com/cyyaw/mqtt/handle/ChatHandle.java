package com.cyyaw.mqtt.handle;


import cn.hutool.json.JSONObject;
import com.cyyaw.mqtt.rabbit.RabbitMqMqtt;
import com.cyyaw.user.service.ChMessageService;
import com.cyyaw.user.service.ChRoomService;
import com.cyyaw.user.service.ChRoomUserService;
import com.cyyaw.user.table.entity.ChMessage;
import com.cyyaw.user.table.entity.ChRoom;
import com.cyyaw.user.table.entity.ChRoomUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 聊天消息处理
 */
@Slf4j
@Service
public class ChatHandle {

    @Autowired
    private ChRoomService chRoomService;

    @Autowired
    private ChMessageService chMessageService;

    @Autowired
    private ChRoomUserService chRoomUserService;

    @Autowired
    private AmqpTemplate amqpTemplate;


    public void handle(JSONObject msgData) {
        String userId = msgData.getStr("userId");
        String roomId = msgData.getStr("roomId");
        // 保存聊天消息
        ChRoom chRoom = chRoomService.findByTid(roomId);
        if (null != chRoom) {
            ChMessage msg = msgData.toBean(ChMessage.class);
            ChMessage save = chMessageService.save(msg);
            String restStr = new JSONObject(save).toString();
            // 把消息转发给其它人
            List<ChRoomUser> chRoomUserList = chRoomUserService.findRoomUserByRoomId(roomId);
            if (null != chRoomUserList && chRoomUserList.size() > 0) {
                for (int i = 0; i < chRoomUserList.size(); i++) {
                    ChRoomUser chRoomUser = chRoomUserList.get(i);
                    String uid = chRoomUser.getUserId();
                    if (!userId.equals(uid)) {
                        log.info("【chat回复消息】  {}", restStr);
                        amqpTemplate.convertAndSend(RabbitMqMqtt.MQTT_EXCHANGE, "chat." + uid, restStr);
                    }
                }
            }
        }
    }
}
