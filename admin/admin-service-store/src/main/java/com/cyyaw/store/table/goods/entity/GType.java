package com.cyyaw.store.table.goods.entity;

import com.cyyaw.jpa.util.entity.TreeEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "g_type")
@org.hibernate.annotations.Table(appliesTo = "g_type", comment = "品类表")
public class GType implements TreeEntity<Integer>, Serializable {
    private static final long serialVersionUID = 15687556273933758L;

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
    // =================================================================================
    @Basic
    @Column(name = "enterprise_code", columnDefinition = "varchar(32) COMMENT '所属企业e_enterprise表code'")
    private String enterpriseCode;

    @Basic
    @Column(name = "app_id", columnDefinition = "varchar(32) COMMENT '应用ID'")
    private String appId;

    // =================================================================================

    @Basic
    @Column(name = "pid", columnDefinition = "varchar(32) COMMENT '父级ID'")
    private String pid;
    @Basic
    @Column(name = "tree_code", columnDefinition = "varchar(32) not null default '' COMMENT '树码(一级三位)'")
    private String treeCode;

    // =================================================================================
    @Basic
    @Column(name = "sort", columnDefinition = "int default '1' COMMENT '排序'")
    private Integer sort;

    @Basic
    @Column(name = "name", columnDefinition = "varchar(32) COMMENT '品类名称'")
    private String name;
    @Basic
    @Column(name = "img", columnDefinition = "varchar(255) COMMENT '图标'")
    private String img;

    @Basic
    @Column(name = "status", columnDefinition = "int COMMENT '状态{1:显示,0:隐藏}'")
    private Integer status;

}
