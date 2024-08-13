package com.cyyaw.mqtt.entity.base;

import lombok.Data;


@Data
public class PkType {

    /**
     * 类型
     */
    private String type;

    /**
     *  平台编码
     */
    private String registerId;

    /**
     * 厂家编码
     */
    private String producer;

    /**
     * 设备编码
     */
    private String deviceId;


    /**
     * 请求流水号
     */
    private String txnNo;
}
