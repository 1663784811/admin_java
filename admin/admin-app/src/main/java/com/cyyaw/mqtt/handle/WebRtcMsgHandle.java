package com.cyyaw.mqtt.handle;


import cn.hutool.json.JSONObject;
import com.cyyaw.mqtt.MqttService;
import com.cyyaw.mqtt.MsgData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class WebRtcMsgHandle {

    @Autowired
    private MqttService mqttService;


    // 在线用户表
    public static ConcurrentHashMap<String, UserBean> userBeans = new ConcurrentHashMap<>();

    // 在线房间表
    public static ConcurrentHashMap<String, RoomInfo> rooms = new ConcurrentHashMap<>();


    public void handle(String to, String data) {
        System.out.println("==========" + to + data);
        EventData eventData = new JSONObject(data).toBean(EventData.class);

        switch (eventData.getEventName()) {
            // 申请房间
            case "__create":
                createRoom(data, eventData.getData());
                break;
            // 邀请
            case "__invite":
                invite(data, eventData.getData());
                break;
            // ====  响铃
            case "__ring":
                ring(data, eventData.getData());
                break;
            // 取消
            case "__cancel":
                cancel(data, eventData.getData());
                break;
            // 拒绝
            case "__reject":
                reject(data, eventData.getData());
                break;
            // 加入
            case "__join":
                join(data, eventData.getData());
                break;
            // ice 数据包
            case "__ice_candidate":
                iceCandidate(data, eventData.getData());
                break;
            // 提供 数据名
            case "__offer":
                offer(data, eventData.getData());
                break;
            // answer数据包
            case "__answer":
                answer(data, eventData.getData());
                break;
            // 离开
            case "__leave":
                leave(data, eventData.getData());
                break;
            // 音频
            case "__audio":
                transAudio(data, eventData.getData());
                break;
            // 断开连接
            case "__disconnect":
                disconnet(data, eventData.getData());
                break;
            default:
                break;
        }


    }


    // 创建房间
    private void createRoom(String message, Map<String, Object> data) {
        String room = UUID.randomUUID().toString() + System.currentTimeMillis();
        String userId = (String) data.get("userID");
        System.out.println(String.format(" 创建房间 createRoom:%s ", room));
        RoomInfo roomParam = rooms.get(room);
        // 没有这个房间
        if (roomParam == null) {
            int size = (int) Double.parseDouble(String.valueOf(data.get("roomSize")));
            // 创建房间
            RoomInfo roomInfo = new RoomInfo();
            roomInfo.setMaxSize(size);
            roomInfo.setRoomId(room);
            roomInfo.setUserId(userId);
            // 将房间储存起来
            rooms.put(room, roomInfo);
            CopyOnWriteArrayList<UserBean> copy = new CopyOnWriteArrayList<>();
            // 将自己加入到房间里
            UserBean my = userBeans.get(userId);
            copy.add(my);
            rooms.get(room).setUserBeans(copy);
            // 发送给自己
            EventData send = new EventData();
            send.setEventName("__peers");
            Map<String, Object> map = new HashMap<>();
            map.put("connections", "");
            map.put("you", userId);
            map.put("roomSize", size);
            map.put("room", room);
            send.setData(map);
            sendMsg(my, new JSONObject(send).toString());
        }
    }

    // 首次邀请
    private void invite(String message, Map<String, Object> data) {
        String userList = (String) data.get("userList");
        String room = (String) data.get("room");
        // 对方Id
        String inviteId = (String) data.get("inviteID");
        boolean audioOnly = (boolean) data.get("audioOnly");
        String[] users = userList.split(",");

        System.out.println(String.format("room:%s,%s invite %s audioOnly:%b", room, inviteId, userList, audioOnly));
        // 给其他人发送邀请
        for (String user : users) {
            UserBean userBean = userBeans.get(user);
            if (userBean != null) {
                sendMsg(userBean, message);
            }
        }


    }

    // 响铃回复
    private void ring(String message, Map<String, Object> data) {
        String room = (String) data.get("room");
        String inviteId = (String) data.get("toID");

        UserBean userBean = userBeans.get(inviteId);
        if (userBean != null) {
            sendMsg(userBean, message);
        }
    }

    // 取消拨出
    private void cancel(String message, Map<String, Object> data) {
        String room = (String) data.get("room");
        String userList = (String) data.get("userList");
        String[] users = userList.split(",");
        for (String userId : users) {
            UserBean userBean = userBeans.get(userId);
            if (userBean != null) {
                sendMsg(userBean, message);
            }
        }
        if (null != room && rooms.get(room) != null) {
            rooms.remove(room);
        }
    }

    // 拒绝接听
    private void reject(String message, Map<String, Object> data) {
        String room = (String) data.get("room");
        String toID = (String) data.get("toID");
        UserBean userBean = userBeans.get(toID);
        if (userBean != null) {
            sendMsg(userBean, message);
        }
        RoomInfo roomInfo = rooms.get(room);
        if (roomInfo != null) {
            if (roomInfo.getMaxSize() == 2) {
                rooms.remove(room);
            }
        }


    }

    // 加入房间
    private void join(String message, Map<String, Object> data) {
        String room = (String) data.get("room");
        String userID = (String) data.get("userID");

        RoomInfo roomInfo = rooms.get(room);

        int maxSize = roomInfo.getMaxSize();
        CopyOnWriteArrayList<UserBean> roomUserBeans = roomInfo.getUserBeans();

        //房间已经满了
        if (roomUserBeans.size() >= maxSize) {
            return;
        }
        UserBean my = userBeans.get(userID);
        // 1. 將我加入到房间
        roomUserBeans.add(my);
        roomInfo.setUserBeans(roomUserBeans);
        rooms.put(room, roomInfo);

        // 2. 返回房间里的所有人信息
        EventData send = new EventData();
        send.setEventName("__peers");
        Map<String, Object> map = new HashMap<>();

        String[] cons = new String[roomUserBeans.size()];
        for (int i = 0; i < roomUserBeans.size(); i++) {
            UserBean userBean = roomUserBeans.get(i);
            if (userBean.getUserId().equals(userID)) {
                continue;
            }
            cons[i] = userBean.getUserId();
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cons.length; i++) {
            if (cons[i] == null) {
                continue;
            }
            sb.append(cons[i]).append(",");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }

        // 给自己发送消息
        map.put("connections", sb.toString());
        map.put("you", userID);
        map.put("roomSize", roomInfo.getMaxSize());
        send.setData(map);
        sendMsg(my, new JSONObject(send).toString());


        // 3. 给房间里的其他人发送消息
        EventData newPeer = new EventData();
        newPeer.setEventName("__new_peer");
        Map<String, Object> sendMap = new HashMap<>();
        sendMap.put("userID", userID);
        newPeer.setData(sendMap);
        for (UserBean userBean : roomUserBeans) {
            if (userBean.getUserId().equals(userID)) {
                continue;
            }
            sendMsg(userBean, new JSONObject(newPeer).toString());
        }


    }

    // 切换到语音接听
    private void transAudio(String message, Map<String, Object> data) {
        String userId = (String) data.get("userID");
        UserBean userBean = userBeans.get(userId);
        if (userBean == null) {
            System.out.println("用户 " + userId + " 不存在");
            return;
        }
        sendMsg(userBean, message);
    }

    // 意外断开
    private void disconnet(String message, Map<String, Object> data) {
        String userId = (String) data.get("userID");
        UserBean userBean = userBeans.get(userId);
        if (userBean == null) {
            System.out.println("用户 " + userId + " 不存在");
            return;
        }
        sendMsg(userBean, message);
    }

    // 发送offer
    private void offer(String message, Map<String, Object> data) {
        String userId = (String) data.get("userID");
        UserBean userBean = userBeans.get(userId);
        sendMsg(userBean, message);
    }

    // 发送answer
    private void answer(String message, Map<String, Object> data) {
        String userId = (String) data.get("userID");
        UserBean userBean = userBeans.get(userId);
        if (userBean == null) {
            System.out.println("用户 " + userId + " 不存在");
            return;
        }
        sendMsg(userBean, message);

    }

    // 发送ice信息
    private void iceCandidate(String message, Map<String, Object> data) {
        String userId = (String) data.get("userID");
        UserBean userBean = userBeans.get(userId);
        if (userBean == null) {
            System.out.println("用户 " + userId + " 不存在");
            return;
        }
        sendMsg(userBean, message);
    }

    // 离开房间
    private void leave(String message, Map<String, Object> data) {
        String room = (String) data.get("room");
        String userId = (String) data.get("fromID");
        if (userId == null) return;
        // 获取房间信息
        RoomInfo roomInfo = rooms.get(room);
        // 获取房间里面用户列表
        CopyOnWriteArrayList<UserBean> roomInfoUserBeans = roomInfo.getUserBeans();
        // 给房间其他人发送离开的消息
        for (UserBean userBean : roomInfoUserBeans) {
            // 排除自己
            if (userId.equals(userBean.getUserId())) {
                roomInfoUserBeans.remove(userBean);
                continue;
            }
            // 发送消息
            sendMsg(userBean, message);
        }


        if (roomInfoUserBeans.size() == 1) {
            System.out.println("房间里只剩下一个人");
            if (roomInfo.getMaxSize() == 2) {
                rooms.remove(room);
            }
        }

        if (roomInfoUserBeans.size() == 0) {
            System.out.println("房间无人");
            rooms.remove(room);
        }
    }


    private static final Object object = new Object();

    /**
     * 给不同设备发送消息
     */
    private void sendMsg(UserBean userBean, String data) {
        String userId = userBean.getUserId();
        MsgData msgData = new MsgData();
        msgData.setData(data);
        msgData.setFrom("");
        msgData.setTo(userId);
        mqttService.sendWebrtc(userId, new JSONObject(msgData).toString());
    }


}
