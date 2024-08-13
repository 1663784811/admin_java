package com.cyyaw.mqtt.entity;

import com.sun.xml.bind.v2.model.core.ID;
import lombok.Data;

import java.util.List;

@Data
public class SwDataJson {


    @Data
    public static class PkType {
        /**
         * 类型 Sizeof(long) swData
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
         * 厂家编码 char[registerId_LEN]Q/ZTT 2239-202367
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
         * 模块 XX 数据集 Sizeof[CirCInfo_LEN]
         */
        private List<CirSet> cirSet;
    }

    public static class CirSet {
        /**
         * 模块 XX 在位 char[cirNo_LEN] 1～12
         */
        private String cirNo;
        /**
         * 状态 char[cirState_LEN] 1 开通、2 断开
         */
        private String cirState;

    }


}
