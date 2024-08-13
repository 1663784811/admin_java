package com.cyyaw.jpa.util.entity;

import lombok.Data;

import java.util.List;


/**
 * 数据表信息
 */
@Data
public class TableInfo {

    /**
     * 数据库
     */
    private String database;
    /**
     * 数据表
     */
    private String table;
    /**
     * 表注释
     */
    private String tableNote;
    /**
     * 类型
     */
    private String tableType;

    /**
     * 字段列表
     */
    private List<JavaColumn> javaColumnList;


    private List<FieldInfo> fieldInfoList;

}
