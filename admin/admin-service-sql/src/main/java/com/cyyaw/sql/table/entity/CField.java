package com.cyyaw.sql.table.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "c_field")
@org.hibernate.annotations.Table(appliesTo = "c_field", comment = "字段表")
public class CField implements Serializable{
    private static final long serialVersionUID = 1667271464088991L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic
    @Column(name = "id", columnDefinition = "int auto_increment not null COMMENT 'id'")
    private Integer id;
    @Basic
    @Column(name = "tid", columnDefinition = "varchar(32) unique not null default '' COMMENT 'tid'")
    private String tid;
    @Basic
    @Column(name = "create_time", columnDefinition = "datetime default now() COMMENT '创建时间'")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @Basic
    @Column(name = "del", columnDefinition = "int default '0' COMMENT '是否删除{0:否,1:是}'")
    private Integer del;
    @Basic
    @Column(name = "note", columnDefinition = "varchar(255) default '' COMMENT '备注'")
    private String note;

    // ==================================================

    @Basic
    @Column(name="c_page_components_id", columnDefinition = "varchar(255) COMMENT 'c_page_components表tid'")
    private String cPageComponentsId;

    @Basic
    @Column(name="c_table_id", columnDefinition = "varchar(255) COMMENT 'ctableid表tid'")
    private String cTableId;
    // ==================================================

    @Basic
    @Column(name="column_name", columnDefinition = "varchar(255) COMMENT '字段名'")
    private String columnName;
    @Basic
    @Column(name="control_type", columnDefinition = "varchar(255) COMMENT '控件类型'")
    private String controlType;

    @Basic
    @Column(name="db_type", columnDefinition = "varchar(255) COMMENT '数据库字段类型'")
    private String dbType;
    @Basic
    @Column(name="default_value", columnDefinition = "varchar(255) COMMENT '默认值'")
    private String defaultValue;
    @Basic
    @Column(name="fk_column_name", columnDefinition = "varchar(255) COMMENT '外键表列'")
    private String fkColumnName;
    @Basic
    @Column(name="fk_table_name", columnDefinition = "varchar(255) COMMENT '外键表名'")
    private String fkTableName;
    @Basic
    @Column(name="format", columnDefinition = "varchar(255) COMMENT '格式化'")
    private String format;
    @Basic
    @Column(name="index_type", columnDefinition = "varchar(255) COMMENT '索引类型'")
    private String indexType;
    @Basic
    @Column(name="is_autoincrement", columnDefinition = "int COMMENT '自增{0:否,1:是}'")
    private Integer isAutoincrement;
    @Basic
    @Column(name="is_fk", columnDefinition = "int COMMENT '外键{0:否,1:是}'")
    private Integer isFk;
    @Basic
    @Column(name="is_index", columnDefinition = "int COMMENT '索引{0:否,1:是}'")
    private Integer isIndex;
    @Basic
    @Column(name="is_null", columnDefinition = "int COMMENT '为null{0:否,1:是}'")
    private Integer isNull;
    @Basic
    @Column(name="is_primary", columnDefinition = "int COMMENT '主键{0:否,1:是}'")
    private Integer isPrimary;
    @Basic
    @Column(name="is_require", columnDefinition = "int COMMENT '是否必须{0:否,1:是}'")
    private Integer isRequire;
    @Basic
    @Column(name="is_show_column", columnDefinition = "int COMMENT '显示列表{0:否,1:是}'")
    private Integer isShowColumn;
    @Basic
    @Column(name="is_unique", columnDefinition = "int COMMENT '唯一{0:否,1:是}'")
    private Integer isUnique;
    @Basic
    @Column(name="is_where", columnDefinition = "int COMMENT '查询条件{0:否,1:是}'")
    private Integer isWhere;
    @Basic
    @Column(name="java_type", columnDefinition = "varchar(255) COMMENT 'java类型'")
    private String javaType;
    @Basic
    @Column(name="java_where", columnDefinition = "varchar(255) COMMENT 'java条件'")
    private String javaWhere;
    @Basic
    @Column(name="[key]", columnDefinition = "varchar(255) COMMENT '键值'")
    private String key;
    @Basic
    @Column(name="length", columnDefinition = "int COMMENT '长度'")
    private Integer length;
    @Basic
    @Column(name="message", columnDefinition = "varchar(255) COMMENT '提示'")
    private String message;
    @Basic
    @Column(name="pk_column_name", columnDefinition = "varchar(255) COMMENT '外键主表列'")
    private String pkColumnName;
    @Basic
    @Column(name="pk_table_name", columnDefinition = "varchar(255) COMMENT '外键主表'")
    private String pkTableName;
    @Basic
    @Column(name="reg_str", columnDefinition = "varchar(255) COMMENT '正则表达式'")
    private String regStr;
    @Basic
    @Column(name="title", columnDefinition = "varchar(255) COMMENT '名称'")
    private String title;
    @Basic
    @Column(name="type", columnDefinition = "varchar(255) COMMENT '列表类型'")
    private String type;
}
