package com.cyyaw.mqtt.entity;

import com.sun.xml.bind.v2.model.core.ID;
import lombok.Data;
import org.checkerframework.checker.units.qual.A;

import java.util.List;

@Data
public class MeasureRealtimeJson {


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
    }


    @Data
    public static class Values {
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
        /**
         * 站址 ID char[siteId_LEN]
         */
        private String siteId;
        /**
         * 系统时间
         */
        private String deviceTime;
        /**
         * 设备输入电压
         */
        private String deviceVin;
        /**
         * 设备负载总电流
         */
        private String deviceITotal;
        /**
         * 设备负载总功率
         */
        private String devicepPTotal;
        /**
         * 设备负载总电能
         */
        private String deviceQTotal;
        /**
         * 日设备负载总电能
         */
        private String deviceQTotalYda;
        /**
         * 市电停电状态 0 未停电，1停电
         */
        private String outageState;
        /**
         * 油机发电状态 0 未发电，1发电
         */
        private String generatorState;
        /**
         * 机箱内温度
         */
        private String deviceTemp;
        /**
         * 模块集 Sizeof[CirData_LEN]
         */
        private List<CirSet> cirSet;
    }

    @Data
    public static class CirSet {
        /**
         * 模块序号 char[cirNo_LEN]
         */
        private String cirNo;
        /**
         * 模块容量 16，32，63，100
         */
        private String cirCapacity;
        /**
         * 模块电流 A
         */
        private String cirI;
        /**
         * 模块功率 千瓦
         */
        private String cirP;
        /**
         * 模块电能 千瓦时
         */
        private String cirQ;
        /**
         * 模块前一日电能千瓦时
         */
        private String cirQYda;
        /**
         * 模块备电时长使用量0 代表未启用，大于0浮点数代表h
         */
        private String cirUpsDurationUse;
        /**
         * 模块备电电能使用量0 代表未启用，大于0浮点数代表kWh
         */
        private String cirUpsCapacityUse;
    }

}
