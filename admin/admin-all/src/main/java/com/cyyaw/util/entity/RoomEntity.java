package com.cyyaw.util.entity;


import lombok.Data;

import java.util.Date;


/**
 * 房间
 */
@Data
public class RoomEntity {
    /**
     * tid
     */
    private String tid;
    /**
     * 房类型
     */
    private Integer type;
    /**
     * 房间名称
     */
    private String name;
    /**
     * 头像
     */
    private String face;
    /**
     * 状态
     */
    private Integer status;

    /**
     * 更新时间
     */
    private Date updateTime;
}
