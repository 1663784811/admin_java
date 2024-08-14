



## 系统角色
```text
超级管理员

企业管理员
    企业人员
        管理员
        员工 
        路径：/admin/{eCode}/模块
    APP
        门店管理员
            店长 、员工
            路径：/appAdmin/{appId}/模块
            
        用户
            路径：/app/{appId}/模块
```




















## Mqtt 实时聊天系统

### 流程

#### 1.设备连接服务成功
```text
    当设备连接服务器成功后：
        1.设备主动拉取好友列表报文如下
            设备发布主题: mqtt_service/order/用户ID
                {
                   order:'pullMyFriend'
                }
            设备订阅主题: order/用户Id
                {
                    order:'pullMyFriend',
                    data:[
                        {}
                    ]
                }
        
        2.服务器主动向设备发送离线消息 ( 没有则不发送, 如果是群消息则取最大100条 )
            服务器发布主题:  chat/用户Id
                {
                    userId: '用户ID',
                    roomId: '房间ID',
                    msgType: 0,
                    data: "消息内容"
                }
            服务订阅: mqtt_service/order/用户Id
                {
                    order:'chatMsg',
                    msgId: '已发送成功消息ID'
                }
            服务订阅: mqtt_service/order/用户Id
                {
                    order:'readMsg',
                    msgId: '已读消息ID'
                }
```

#### 2.正常消息发送
```text
    设备订阅: chat/用户Id
        {
            userId: '用户ID',
            roomId: '房间ID',
            msgType: 0,
            data: "消息内容"
        }
    设备发布：mqtt_service/chat/房间ID
        {
            userId: '用户ID',
            roomId: '房间ID',
            msgType: 0,
            data: "消息内容"
        }
```




