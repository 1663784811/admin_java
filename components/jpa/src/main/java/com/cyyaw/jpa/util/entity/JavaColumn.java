package com.cyyaw.jpa.util.entity;

import lombok.Data;

/**
 * 数据表字段
 */
@Data
public class JavaColumn {
    /**
     * 字段名
     */
    private String columnName;
    /**
     * 数据库字段类型
     */
    private String dbType;
    /**
     * 长度
     */
    private Integer length;
    /**
     * java 类型
     */
    private String javaType;
    /**
     * java 字段
     */
    private String javaField;
    /**
     * 默认值
     */
    private String defaultValue;
    /**
     * 注释
     */
    private String note;
    /**
     * 是否自增加
     */
    private Boolean isAutoIncrement;
    /**
     * 是否可以为null
     */
    private Boolean isNull = true;

    /**
     * 是否是主键
     */
    private Boolean isPrimary = false;
    /**
     * 表名
     */
    private String pkTableName;
    /**
     * 指向的列
     */
    private String pkColumnName;
    /**
     * 是否是外键
     */
    private Boolean isFk = false;
    /**
     * 表名
     */
    private String fkTableName;
    /**
     * 指向的列
     */
    private String fkColumnName;
    /**
     * 是否是唯一
     */
    private Boolean isUnique = false;
    /**
     * 是否是索引
     */
    private Boolean isIndex = false;
    /**
     * 索引类型
     */
    private String indexType;

    /**
     * mybatis 类型
     */
    private String mybatisType;

}
