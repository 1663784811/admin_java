package com.cyyaw.mqtt.entity;


import lombok.Data;


/**
 * B.2.1 申请账号
 * 硬件发布：get_dev_pwd/厂家编码/设备编码。
 * 硬件订阅：topic_json_pwd/厂家编码/设备编码。
 */

public class PwDev {
    @Data
    public static class PkType {
        /**
         * 类型 Sizeof(long) GET_pwd
         */
        private String type;
        /**
         * 请求流水号 int1970 年01 月01 日00时00分00 秒起至现在的总毫秒数示例：13 位数字时间戳1684203847167
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

    }

    @Data
    public static class Values {
        /**
         * 初始账号
         */
        private String account;

        /**
         * 初始密码
         */
        private String pwd;
    }
}
