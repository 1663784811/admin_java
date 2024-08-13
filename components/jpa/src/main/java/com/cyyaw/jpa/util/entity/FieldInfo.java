package com.cyyaw.jpa.util.entity;

import lombok.Data;


/**
 * 字段信息
 */
@Data
public class FieldInfo {

    /**
     * 字段
     */
    private String field;

    /**
     * 类型
     */
    private String type;

    /**
     * 编码
     */
    private String collation;

    /**
     * 是否为null
     */
    private String isNull;

    /**
     * 索引
     */
    private String key;

    /**
     * 默认值
     */
    private String defaultValue;

    /**
     * 其它
     */
    private String extra;


}
