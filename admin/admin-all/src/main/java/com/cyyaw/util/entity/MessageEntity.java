package com.cyyaw.util.entity;


import lombok.Data;

import java.util.Date;

/**
 * 消息实体类
 * @author why
 */
@Data
public class MessageEntity {
    /**
     * 状态
     */
    private Integer status;
    /**
     * 消息
     */
    private String message;
    /**
     * 备注
     */
    private String note;
    /**
     * 时间
     */
    private Date time;
}