package com.cyyaw.mqtt.entity;


import lombok.Data;

import java.util.List;


/**
 *
 */
@Data
public class LoginDev {


    @Data
    public static class PkType {
        /**
         * 类型 Sizeof(long) login
         */
        private String type;
        /**
         * 请求流水号 int防止异步错误，取值为当前毫秒级时间戳
         */
        private String txnNo;
        /**
         * 账号 char[accountAck_LEN]
         */
        private String accountAck;
        /**
         * 密码 char[pwdAck_LEN]
         */
        private String pwdAck;
        /**
         * 平台编码 char[registerId_LEN] 运维监控系统设备ID
         */
        private String registerId;
        /**
         * 厂家编码 char[producer_LEN]
         */
        private String producer;
        /**
         * 设备编码 char[deviceId_LEN] 唯一编码
         */
        private String deviceId;
        /**
         * 设备资产编码 char[deviceAssetId_LEN]
         */
        private String deviceAssetId;

    }


    @Data
    public static class Values {
        /**
         * 系统版本号
         */
        private String softwareVersion;

        /**
         * 4G 协议版本
         */
        private String protocolVersion;

        /**
         * 模块在位数量
         */
        private String cirInPlaceNum;

        /**
         * 模块数据集
         */
        private List<CirSet> cirSet;
    }


    @Data
    public static class CirSet {
        /**
         * 模块序号
         */
        private String cirNo;
        /**
         * 模块类型
         */
        private String cirType;
        /**
         * 模块容量
         */
        private String cirCapacity;
    }


}
