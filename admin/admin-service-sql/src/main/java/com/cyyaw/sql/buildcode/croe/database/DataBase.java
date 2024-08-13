package com.cyyaw.sql.buildcode.croe.database;


import cn.hutool.core.util.StrUtil;
import com.cyyaw.sql.buildcode.croe.entity.java.*;
import com.cyyaw.sql.buildcode.croe.tools.OperationTools;
import com.cyyaw.sql.buildcode.croe.tools.TypeTools;
import org.springframework.util.StringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBase {

    private Connection connection;

    public DataBase(Connection connection) {
        this.connection = connection;
    }

    public DataBase(String driver, String url, String user, String pwd) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        connection = DriverManager.getConnection(url, user, pwd);
    }

    /**
     * 获取数据表信息---- 列表
     */
    public List<JavaData> getTableList(String table) throws SQLException {
        ArrayList<JavaData> javaDataArrayList = new ArrayList<JavaData>();
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet tables = metaData.getTables(connection.getCatalog(), "%", "%", null);
        while (tables.next()) {
            //数据表名
            String table_name = tables.getString("TABLE_NAME");
            if (null != table_name && !table_name.equals("")) {
                JavaData javaData = new JavaData();
                javaData.setDatabase(connection.getCatalog());                           //数据库名
                javaData.setTable(table_name);                                           //表名
                javaData.setTableNote(getTableNotes(table_name));                        //表注释
                javaData.setTableType(tables.getString("TABLE_TYPE"));       //数据表类型  （表 ， 视图，。。。）
                List<JavaColumn> javaColumnList = getColumnList(table_name);
                javaData.setJavaColumns(javaColumnList);                                 //字段
                if (StringUtils.isEmpty(table)) {
                    javaDataArrayList.add(javaData);
                } else if (table.equals(table_name)) {
                    javaDataArrayList.add(javaData);
                }
            }
        }
        return javaDataArrayList;
    }


    /**
     * 数据库类型转换
     */
    private String dbTypeChange(String type) {
        if (type.indexOf("UNSIGNED") != -1) {
            type = type.substring(0, type.indexOf("UNSIGNED") - 1);
        }
        return type;
    }

    /**
     * 获取表字段信息----- 列表
     *
     * @param tableName 数据表名
     * @throws SQLException
     */
    public List<JavaColumn> getColumnList(String tableName) throws SQLException {

        ArrayList<JavaColumn> javaColumnList = new ArrayList<JavaColumn>();
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet columns = metaData.getColumns(connection.getCatalog(), "%", tableName, "%");
        while (columns.next()) {
            JavaColumn javaColumn = new JavaColumn();
            String type = dbTypeChange(columns.getString("TYPE_NAME"));
            //===============================
            javaColumn.setColumnName(columns.getString("COLUMN_NAME"));    //字段名
            javaColumn.setDbType(type);                                          //数据库字段类型
            javaColumn.setMybatisType(TypeTools.dbType2MybatisType(type));
            javaColumn.setLength(columns.getInt("COLUMN_SIZE"));     //长度
            javaColumn.setJavaType(TypeTools.dbType2JavaType(type));             //java 类型
            javaColumn.setJavaField(OperationTools.indexToLowerCase(columns.getString("COLUMN_NAME")));
            javaColumn.setDefaultValue(columns.getString("COLUMN_DEF"));//默认值
            javaColumn.setNote(columns.getString("REMARKS"));        //注释
            javaColumn.setIsAutoIncrement(columns.getString("IS_AUTOINCREMENT").equals("YES"));//是否自增加
            javaColumn.setIsNull(columns.getString("NULLABLE").equals("1"));  //是否可以为null
            //===============================
            javaColumnList.add(javaColumn);
        }
        //==== 获取索引
        List<IndexKey> indexKeyList = getIndexList(tableName);
        //==== 获取主键
        List<PrimaryKeys> primaryKeysList = getPrimaryKeys(tableName);
        //==== 获取外键
        List<ForeignKey> foreignKeyList = getForeignKeys(tableName);
        for (int i = 0; i < javaColumnList.size(); i++) {
            JavaColumn javaColumn = javaColumnList.get(i);
            String columnName = javaColumn.getColumnName();
            for (int j = 0; j < indexKeyList.size(); j++) {
                IndexKey indexKey = indexKeyList.get(j);
                if (indexKey.getColumnName().equals(columnName)) {
                    javaColumn.setIsIndex(true);
                    javaColumn.setIsUnique(indexKey.getIsUnique());
                    javaColumn.setIndexType(indexKey.getIndexType());
                }
            }
            for (int j = 0; j < primaryKeysList.size(); j++) {
                PrimaryKeys primaryKeys = primaryKeysList.get(j);
                if (primaryKeys.getColumnName().equals(columnName)) {
                    javaColumn.setIsPrimary(true);
                }
            }
            for (int j = 0; j < foreignKeyList.size(); j++) {
                ForeignKey foreignKey = foreignKeyList.get(j);
                if (foreignKey.getFkTableNote().equals(tableName) && foreignKey.getFkColumnName().equals(columnName)) {
                    javaColumn.setPkTableName(foreignKey.getPkTableName());
                    javaColumn.setPkColumnName(foreignKey.getPkColumnName());
                }
            }
        }
        return javaColumnList;
    }


    /**
     * 获取索引
     */
    public List<IndexKey> getIndexList(String tableName) throws SQLException {
        List<IndexKey> list = new ArrayList<IndexKey>();
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet indexInfo = metaData.getIndexInfo(connection.getCatalog(), "%", tableName, false, false);
        while (indexInfo.next()) {
            IndexKey indexKey = new IndexKey();
            indexKey.setTableName(indexInfo.getString("TABLE_NAME"));
            indexKey.setColumnName(indexInfo.getString("COLUMN_NAME"));
            indexKey.setIsUnique(indexInfo.getString("NON_UNIQUE").equals("0"));
            indexKey.setIndexType(indexInfo.getString("TYPE"));
            list.add(indexKey);
        }
        return list;
    }


    /**
     * 获取主键列表
     *
     * @param tableName 数据表名
     * @throws SQLException
     */
    public List<PrimaryKeys> getPrimaryKeys(String tableName) throws SQLException {
        List<PrimaryKeys> list = new ArrayList<PrimaryKeys>();
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet primaryKeys = metaData.getPrimaryKeys(connection.getCatalog(), "%", tableName);
        while (primaryKeys.next()) {
            PrimaryKeys primaryKeysList = new PrimaryKeys();
            primaryKeysList.setTableName(primaryKeys.getString("TABLE_NAME"));
            primaryKeysList.setColumnName(primaryKeys.getString("COLUMN_NAME"));
            list.add(primaryKeysList);
        }
        return list;
    }


    /**
     * 获取外键列表
     */
    public List<ForeignKey> getForeignKeys(String taleName) throws SQLException {
        List<ForeignKey> list = new ArrayList<ForeignKey>();
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet foreignKeyResultSet = metaData.getImportedKeys(connection.getCatalog(), "%", taleName);
        while (foreignKeyResultSet.next()) {
            ForeignKey foreignKey = new ForeignKey();
            foreignKey.setFkTableNote(foreignKeyResultSet.getString("FKTABLE_NAME"));
            foreignKey.setFkColumnName(foreignKeyResultSet.getString("FKCOLUMN_NAME"));
            foreignKey.setPkTableName(foreignKeyResultSet.getString("PKTABLE_NAME"));
            foreignKey.setPkColumnName(foreignKeyResultSet.getString("PKCOLUMN_NAME"));
            list.add(foreignKey);
        }
        return list;
    }


    /**
     * 获取表的注释
     *
     * @param tableName
     * @return
     */
    public String getTableNotes(String tableName) throws SQLException {
        String notes = null;
        ResultSet rsBiaoGe = connection.prepareStatement("show table status where Name = '" + tableName + "'").executeQuery();
        if (rsBiaoGe != null && rsBiaoGe.next()) {
            notes = rsBiaoGe.getString("Comment");
        }
        return notes;
    }


    public JavaData getTable(String table) throws SQLException {
        JavaData rest = null;
        if (StrUtil.isNotBlank(table)) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables(connection.getCatalog(), "%", table, null);
            if (tables.next()) {
                //数据表名
                String table_name = tables.getString("TABLE_NAME");
                if (StrUtil.isNotBlank(table_name)) {
                    rest = new JavaData();
                    rest.setDatabase(connection.getCatalog());                           //数据库名
                    rest.setTable(table_name);                                           //表名
                    rest.setTableNote(getTableNotes(table_name));                        //表注释
                    rest.setTableType(tables.getString("TABLE_TYPE"));       //数据表类型  （表 ， 视图，。。。）
                    List<JavaColumn> javaColumnList = getColumnList(table_name);
                    rest.setJavaColumns(javaColumnList);                                 //字段
                }
            }
        }
        return rest;
    }
}















