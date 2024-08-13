package com.cyyaw.mqtt.entity;


import lombok.Data;

import java.util.List;

@Data
public class InfoJson {


    @Data
    public static class PkType {

        /**
         * 类型 Sizeof(long) GET_pwd_ACK
         */
        private String type;
        /**
         * 请求流水号 int防止异步错误，取值为当前毫秒级时间戳
         */
        private String txnNo;
    }

    @Data
    public static class Values {
        /**
         * 厂家编码 char[producer_LEN]
         */
        private String producer;
        /**
         * 设备编码 char[deviceId_LEN] 唯一标识5 deviceAssetId 设备资产编码 char[deviceAssetId_LEN]
         */
        private String deviceId;
        /**
         * 系统版本号 char[deviceId_LEN] V 大版本号.子版本号.Q/ZTT 2239-202340on 日期，如V1.0.20230508
         */
        private String softwareVersi;
        /**
         * 4G 协议版本 char[deviceId_LEN]
         */
        private String protocolVersion;
        /**
         * 物联网卡号 char[iccid_LEN]
         */
        private String iccid;
        /**
         * 系统时间
         */
        private String deviceTime;
        /**
         * 无线模块在位状态 0 不在位，1 在位。
         */
        private String onlineState;
        /**
         * 模块在位数量 char[cirInPlaceNum_LEN] 一共有多少个模块在位。
         */
        private String cirInPlaceNum;
        /**
         * 模块数据集Sizeof(cirPInfoDescribe)
         */
        private List<CirSet> cirSet;

    }


    public static class CirSet {
        /**
         * 模块序号 char[cirNo_LEN]
         */
        private String cirNo;
        /**
         * 模块类型char[cirInPlaceNum_LEN]0 直流，1 交流
         */
        private String cirType;
        /**
         * 模块容量 char[cirCapacity_LEN] 16A、32A、63A.......
         */
        private String cirCapacity;
    }

}
