package com.cyyaw.mqtt.entity;


import lombok.Data;

@Data
public class DeviceTimeCheckDev {


    @Data
    public static class PkType {
        /**
         * 类型 Sizeof(long) deviceTime_CHECK
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
         * 设备编码 char[deviceId_LEN] 唯一标识
         */
        private String deviceId;
        /**
         * 厂家编码 char[producer_LEN] producer
         */
        private String producer;
    }


    @Data
    public static class Values {
        /**
         * 时钟同步 Sizeof(deviceTime)若不为空，按time值同步；若为空，按某一时钟源同步。每天进行一次时钟源校准
         */
        // private String Time;
    }


}
