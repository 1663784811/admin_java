package com.cyyaw.mqtt.entity;

import com.sun.xml.bind.v2.model.core.ID;
import lombok.Data;

@Data
public class SendAlarmDev {


    @Data
    public static class PkType {
        /**
         * 类型 Sizeof(long) alarm
         */
        private String type;
        /**
         * 账号 char[accountAck_LEN]
         */
        private String accountAck;
        /**
         * 密码 char[pwdAck_LEN]
         */
        private String pwdAck;
        /**
         * 请求流水号 int防止异步错误，取值为当前毫秒级时间戳
         */
        private String txnNo;
        /**
         * 平台编码 char[registerId_LEN] 运维监控系统设备ID
         */
        private String registerId;
        /**
         * 厂家编码 char[registerId_LEN]
         */
        private String producer;
        /**
         * 设备编码 char[deviceId_LEN] 唯一标识
         */
        private String deviceId;

    }

    @Data
    public static class Values {
        /**
         * 站址 ID
         */
        private String siteId;
        /**
         * 告警类型0 无/1 过流/2 过压/3 欠压/4 过载/5断市电/6过温/7过载/8 一级低压脱离
         */
        private String alarmType;
        /**
         * TAlarm TAlarm 按实际告警内容填写
         */
//        private String TAlarm;

    }




    @Data
    public static class TAlarm {
        /**
         * 模块 XX 在位0 主机告警/1～12 模块XX告警
         */
        private String cirNo;
        /**
         * 模块 XX 告警类型0 无/1 通信/2 故障/3过载保护/4 过载锁定3 sw 是否已断开 0 断开/1 开通
         */
        private String ciralarmType;

    }

}
