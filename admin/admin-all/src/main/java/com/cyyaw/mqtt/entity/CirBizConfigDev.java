package com.cyyaw.mqtt.entity;

import lombok.Data;

import java.util.List;

@Data
public class CirBizConfigDev {


    @Data
    public static class PkType {
        /**
         * 类型 Sizeof(long) cirBizInfo
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
         * 设备编码 char[deviceId_LEN] 唯一标识6 siteId 站址 ID char[siteId_LEN]
         */
        private String deviceId;
    }

    @Data
    public static class Values {
        /**
         * 免责时段起止时间设置格式HHMM.HHMM，以小数点分隔，默认2400.2400
         */
        private String exemption;
        /**
         * 临时授权天数 int
         */
        private String TempAuthDays;

        /**
         * 一级低压脱离电压设置默认值46.5
         */
        private String l1lpdv;
        /**
         * 一级低压脱离恢复电压设置
         */
        private String l1lprv;
        /**
         * 设备温度阈值
         */
        private String deviceTempT;
        /**
         * 模块在位数量 char[cirInPlaceNum_LEN] 1～12
         */
        private String cirInPlaceNum;
        /**
         * 模块数据集sizeof(CirBInfo_Describe)
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
         * 模块租户设置char[cirTenant_LEN]设置模块XX 租户：客户编码XX+业务编码YY XX：00：无租户、01：移动、02：联通、03：电信、04：广电、05：智联、06：能源、07：铁塔YY：00：无业务、01：传输设备、02：2G、03：3G、04：4G、05：5G、06：6G、07：拉远、08：空调/新风、09：其他
         */
        private String cirTenant;
        /**
         * 订单编号 char[orderId_LEN] NA 或订单号
         */
        private String orderId;
        /**
         * 模块授权设置远程开关状态，0未授权，1 已授权，2临时授权
         */
        private String cirAuth;
        /***
         * 模块开关设置0 撤销授权，1授权接通，2 临时授权接通
         */
        private String cirswitch;
        /**
         * 模块免责时段设置0 取消免责时段，1 使用免责时段
         */
        private String cirexemption;
        /**
         * 模块断电时段 1 使能设置0 取消自动断电，1 使用自动断电
         */
        private String cirexemption1Enable;
        /**
         * 模块断电时段 1 设置格式HHMM.HHMM，以小数点分隔，小数点前代表断开时间，小数点后代表开通时间。
         */
        private String cirexemption1Period;
        /**
         * 模块断电时段 2 使能设置0 取消自动断电，1 使用自动断电
         */
        private String cirexemption2Enable;
        /**
         * 模块断电时段 2 设置 HHMM.HHMM
         */
        private String cirexemption2Period;
        /**
         * 模块断电时段 3 使能设置0 取消自动断电，1 使用自动断电
         */
        private String cirexemption3Enable;
        /**
         * 模块断电时段 3 设置 HHMM.HHMM
         */
        private String cirexemption3Period;
        /**
         * 模块断电时段 4 使能设置0 取消自动断电，1 使用自动断电
         */
        private String cirexemption4Enable;
        /**
         * 模块断电时段 4 设置 HHMM.HHMM
         */
        private String cirexemption4Period;
        /**
         * 模块备电电能设置0 代表未启用，默认不启用。非0 浮点数代表kWh
         */
        private String cirUpsCapacity;
        /**
         * 模块备电时长设置0 代表未启用，默认不启用。
         */
        private String cirUpsDuration;
        /**
         * 模块一级低压脱离使能0 不启用一级低压脱离，1 启用一级低压脱离
         */
        private String cirL1lpEnable;
    }

}
