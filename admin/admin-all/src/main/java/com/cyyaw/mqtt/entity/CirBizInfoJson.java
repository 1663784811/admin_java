package com.cyyaw.mqtt.entity;

import lombok.Data;

import java.util.List;

@Data
public class CirBizInfoJson {


    @Data
    public static class PkType {
        /**
         * 类型 Sizeof(long) CirBizInfo
         */
        private String type;
        /**
         * 请求流水号 int防止异步错误，取值为当前毫秒级时间戳
         */
        private String txnNo;
        /**
         * 设备资产编码默认为空，12 位字母数字组合
         */
        private String deviceAssetId;
    }

    @Data
    public static class Values {
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
         * 设备编码 char[deviceId_LEN] 唯一标识
         */
        private String deviceId;

        /**
         * 厂家编码 char[producer_LEN]
         */
        private String producer;

        /**
         * 站址 ID char[siteId_LEN]
         */
        private String siteId;
        /**
         * 模块在位数量char[cirInPlaceNum_LEN]1～12
         */
        private String cirInPlaceNum;
        /**
         * 模块授权数量 char[cirAuthNum _LEN] 1～12
         */
        private String cirAuthNum;
        /**
         * 免责时段起止时间HHMM.HHMM13 TempAuthDays 临时授权天数 int
         */
        private String exemption;
        /**
         * 一级低压脱离电压46.5
         */
        private String l1lpdv;
        /**
         * 一级低压脱离恢复电压
         */
        private String l1lprv;
        /**
         * 设备温度阈值 150℃
         */
        private String deviceTempT;
        /**
         * Values Sizeof(TThreshold)
         */
        private String Values;
        /**
         * 模块数据集Sizeof[CirBInfo_Describe]
         */
        private List<CirSet> cirSet;
    }


    public static class CirSet {
        /**
         * 模块序号 char[cirNo_LEN]
         */
        private String cirNo;
        /**
         * 模块租户设置 char[cirTenant_LEN]设置模块XX 租户：客户编码XX+业务编码YYXX：00：无租户、01：移动、02：联通、03：电信、04：广电、05：智联、06：能源、07：铁塔YY：00：无业务、01：传输设备、02：2G、03：3G、04：4G、05：5G、06：6G、07：拉远、08：空调/新风、09：其他
         */
        private String cirTenant;
        /**
         * 订单编号 char[orderId_LEN] NA 或订单号
         */
        private String orderId;
        /**
         * 模块在位状态 0 不在位，1在位
         */
        private String cirInPlaceState;
        /**
         * 模块授权设置远程开关状态，0未授权，1 已授权，2临时授权
         */
        private String cirAuth;
        /**
         * 模块开关设置 1 已开，0 关闭
         */
        private String cirswitch;
        /**
         * 模块通断次数
         */
        private String cirswitchNum;
        /**
         * 模块免责时段设置0 取消免责时段，1 使用免责时段
         */
        private String cirexemption;
        /**
         * 模块断电时段 1使能设置0 取消自动断电，1 使用自动断电
         */
        private String cirexemption1Enable;
        /**
         * 模块断电时段 1设置格式HHMM.HHMM，以小数点分隔，小数点前代表断开时间，小数点后代表开通时间。
         */
        private String cirexemption1Period;
        /**
         * 模块断电时段 2使能设置0 取消自动断电，1 使用自动断电
         */
        private String cirexemption2Enable;
        /**
         * 模块断电时段 2设置HHMM.HHMM
         */
        private String cirexemption2Period;
        /**
         * 模块断电时段 3使能设置0 取消自动断电，1 使用自动断电
         */
        private String cirexemption3Enable;
        /**
         * 模块断电时段 3设置HHMM.HHMM
         */
        private String cirexemption3Period;
        /**
         * 模块断电时段 4 0 取消自动断电，
         */
        private String cirexemption4Enabl;
        /**
         * 模块断电时段 4设置HHMM.HHMM
         */
        private String cirexemption4Period;
        /**
         * 模块备电电能设置0 代表未启用，默认不启用。非0 浮点数代表kWh
         */
        private String cirUpsCapacity;
        /**
         * 模块备电时长设置0 代表未启用，默认不启用。非0 浮点数代表h
         */
        private String cirUpsDuration;
        /**
         * 模块一级低压脱离使能0 不启用一级低压脱离，1 启用一级低压脱离
         */
        private String cirL1lpEnable;
        /**
         * 模块断开状态未断开0，手动断开1，远程断开2，定时段开3，备电时长断开4，低压脱离断开5，备电电能断开6，过载保护断开7，取消授权断开8，电池保护断开9。
         */
        private String cirBreakState;

    }

}
