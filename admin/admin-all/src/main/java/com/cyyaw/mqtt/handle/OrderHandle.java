package com.cyyaw.mqtt.handle;


import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.cyyaw.mqtt.rabbit.RabbitMqMqtt;
import com.cyyaw.user.service.ChRoomService;
import com.cyyaw.user.service.UUserService;
import com.cyyaw.user.table.entity.ChFriendsUser;
import com.cyyaw.user.table.entity.ChRoom;
import com.cyyaw.user.table.entity.UUser;
import com.cyyaw.util.entity.FriendsEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 指令消息处理
 */
@Slf4j
@Service
public class OrderHandle {

    @Autowired
    private UUserService userService;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private ChRoomService chRoomService;


    public void handle(String userId, JSONObject msgData) {
        // 指令
        String order = msgData.getStr("order");
        if ("pullMyFriend".equals(order)) {
            // 查询用户
            List<ChFriendsUser> friendsUserList = userService.myFriends(userId);
            JSONArray array = new JSONArray();
            for (int i = 0; i < friendsUserList.size(); i++) {
                ChFriendsUser friendsUser = friendsUserList.get(i);
                FriendsEntity friends = new FriendsEntity();
                UUser toUser = friendsUser.getToUser();
                friends.setTid(friendsUser.getTid());
                friends.setRoomId(friendsUser.getRoomId());
                friends.setUserId(toUser.getTid());
                friends.setAppId(toUser.getAppId());
                friends.setAccount(toUser.getAccount());
                friends.setSex(toUser.getSex());
                friends.setNickName(toUser.getNickName());
                friends.setPhone(toUser.getPhone());
                friends.setFace(toUser.getFace());
                friends.setIntroduceSign(toUser.getIntroduceSign());
                array.add(friends);
            }
            JSONObject rest = new JSONObject();
            rest.set("order", order);
            rest.set("data", array);
            String restMsg = rest.toString();
            log.info("【 order 回复消息】: " + restMsg);
            amqpTemplate.convertAndSend(RabbitMqMqtt.MQTT_EXCHANGE, "order." + userId, restMsg);
        } else if ("pullRoom".equals(order)) {
            // 拉取房间
            List<ChRoom> myRoom = chRoomService.findMyRoom(userId);
            JSONObject rest = new JSONObject();
            String restMsg = rest.toString();
            amqpTemplate.convertAndSend(RabbitMqMqtt.MQTT_EXCHANGE, "order." + userId, restMsg);
        } else if ("getMyFriend".equals(order)) {
            // 拉取房间
            List<ChRoom> myRoom = chRoomService.findMyRoom(userId);
            JSONObject rest = new JSONObject();
            String restMsg = rest.toString();
            amqpTemplate.convertAndSend(RabbitMqMqtt.MQTT_EXCHANGE, "order." + userId, restMsg);
        }
    }


}
