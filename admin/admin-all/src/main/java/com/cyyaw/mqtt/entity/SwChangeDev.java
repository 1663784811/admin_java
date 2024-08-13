package com.cyyaw.mqtt.entity;

import lombok.Data;

@Data
public class SwChangeDev {

    @Data
    public static class PkType {
        /**
         * 类型 Sizeof(long) swChange
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
         * 站址 ID char[siteId_LEN]
         */
        private String siteId;
        /**
         * 模块 XX 在位 char[cirNo_LEN] 1～12
         */
        private String cirNo;
        /**
         * 控制指令 1 开通、2 断开
         */
        private String sw;
    }


}
