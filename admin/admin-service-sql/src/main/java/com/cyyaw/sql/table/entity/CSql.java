package com.cyyaw.sql.table.entity;

import com.cyyaw.jpa.util.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "c_sql")
@org.hibernate.annotations.Table(appliesTo = "c_sql", comment = "查询语句")
public class CSql implements BaseEntity<Integer>, Serializable {
    private static final long serialVersionUID = 1665182321135876L;

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
    @Column(name = "name", columnDefinition = "varchar(32) not null COMMENT '名称'")
    private String name;
    @Basic
    @Column(name = "type", columnDefinition = "int default '0' COMMENT '类型{0:查询,1:保存}'")
    private Integer type;
    //===
    @Basic
    @Column(name = "count_sql", columnDefinition = "text COMMENT '查询count数量'")
    private String countSql;
    @Basic
    @Column(name = "content_sql", columnDefinition = "text COMMENT '查询sql内容'")
    private String contentSql;
    //===
    @Basic
    @Column(name = "main_table", columnDefinition = "varchar(32) COMMENT '主表'")
    private String mainTable;
    @Basic
    @Column(name = "main_id", columnDefinition = "varchar(32) COMMENT '主表ID字段'")
    private String mainId;
    @Basic
    @Column(name = "inset_sql", columnDefinition = "text COMMENT '插入sql内容'")
    private String insetSql;
    @Basic
    @Column(name = "update_sql", columnDefinition = "text COMMENT '更新sql内容'")
    private String updateSql;

    @Basic
    @Column(name = "del_sql", columnDefinition = "text COMMENT '删除sql内容'")
    private String delSql;

    @Basic
    @Column(name = "mount_fields", columnDefinition = "text COMMENT '外挂字段'")
    private String mountFields;

    @Basic
    @Column(name = "login", columnDefinition = "int default '1' COMMENT '是否需要登录{0:否,1:是}'")
    private Integer login;

    @Basic
    @Column(name = "status", columnDefinition = "int default '0' COMMENT '状态{0:启用,1:停用}'")
    private Integer status;
}
