package com.cyyaw.mqtt.entity;

import lombok.Data;

@Data
public class CirBizInfoDev {

    @Data
    public static class PkType {
        /**
         * 类型 Sizeof(long) measureRealtime
         */
        private String type;
        /**
         * 请求流水号 int防止异步错误，取值为当前毫秒级时间戳
         */
        private String txnNo;
        /**
         * 平台编码 char[registerId_LEN] 运维监控系统设备ID
         */
        private String registerId;
        /**
         * 厂家编码 char[producer_LEN]
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
         * 设备编码 char[deviceId_LEN] 唯一标识
         */
        private String deviceId;
    }

}
