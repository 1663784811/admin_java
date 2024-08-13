package com.cyyaw.sql.buildcode.croe.entity.java;

import lombok.Data;

/**
 * 外键
 */
@Data
public class ForeignKey {
    private String fkTableNote;  //表名
    private String fkColumnName;  //表名
    private String pkTableName;   //指向的表名
    private String pkColumnName;  //指向的列
}
