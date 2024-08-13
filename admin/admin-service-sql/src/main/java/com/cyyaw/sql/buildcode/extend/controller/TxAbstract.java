package com.cyyaw.sql.buildcode.extend.controller;

import com.cyyaw.sql.buildcode.croe.entity.java.JavaColumn;
import com.cyyaw.sql.buildcode.croe.entity.java.JavaData;
import com.cyyaw.sql.buildcode.extend.CodeTemplate;

import java.util.List;

/**
 * @author why
 */
public abstract class TxAbstract implements CodeTemplate {
    protected String table;
    protected JavaColumn primaryKey;
    protected List<JavaColumn> javaColumns;

    @Override
    public void setJavaData(JavaData javaData) {
        this.table = javaData.getTable();
        this.javaColumns = javaData.getJavaColumns();
        for (int i = 0; i < javaColumns.size(); i++) {
            JavaColumn javaColumn = javaColumns.get(i);
            Boolean isPrimary = javaColumn.getIsPrimary();
            if (isPrimary) {
                primaryKey = javaColumn;
            }
        }
    }


}
