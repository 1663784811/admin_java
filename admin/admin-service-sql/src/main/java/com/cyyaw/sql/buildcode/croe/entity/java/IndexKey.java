package com.cyyaw.sql.buildcode.croe.entity.java;

import lombok.Data;

/**
 * 索引
 */
@Data
public class IndexKey {
    private String tableName;   //表名
    private String columnName;  //指向的列
    private String indexType;   //索引类型
    private Boolean isUnique = false; //是否是唯一
}
