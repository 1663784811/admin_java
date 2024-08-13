package com.cyyaw.mqtt.entity;


import lombok.Data;

@Data
public class SwDataDev {


    @Data
    public static class PkType {
        /**
         * 类型 Sizeof(long) swData
         */
        private String type ;
        /**
         * 请求流水号 int防止异步错误，取值为当前毫秒级时间戳
         */
        private String txnNo ;
        /**
         * 平台编码 char[registerId_LEN] 运维监控系统设备ID
         */
        private String registerId ;
        /**
         *  厂家编码 char[registerId_LEN]
         */
        private String producer;
        /**
         * 设备编码 char[deviceId_LEN] 唯一标识
         */
        private String deviceId ;
        /**
         *  账号 char[accountAck_LEN]
         */
        private String accountAck;
        /**
         * 密码 char[pwdAck_LEN]
         */
        private String pwdAck ;

    }

    @Data
    public static class Values {
        /**
         * 设备编码 char[deviceId_LEN] 唯一标识
         */
        private String deviceId ;
    }

}
