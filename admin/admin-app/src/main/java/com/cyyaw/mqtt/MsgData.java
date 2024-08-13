package com.cyyaw.mqtt;


import lombok.Data;

/**
 * 消息数据
 */
@Data
public class MsgData {

    private String type;

    private String data;

    private String from;

    private String to;

}
