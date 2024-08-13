package com.cyyaw.sql.buildcode.croe.entity.java;

import lombok.Data;

import java.util.List;

@Data
public class JavaData {
    private String database;                //数据库
    private String table;                   //数据表
    private String tableNote;               //表注释
    private String tableType;               //类型
    private List<JavaColumn> javaColumns;   //字段列表
}
