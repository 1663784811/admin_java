package com.cyyaw.user.table.entity;

import com.cyyaw.jpa.util.entity.TreeEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "t_department")
@org.hibernate.annotations.Table(appliesTo = "t_department", comment = "部门表")
public class TDepartment implements TreeEntity<Integer>, Serializable {

    private static final long serialVersionUID = 156878267170401L;

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

    // =================================================================================
    @Basic
    @Column(name = "name", columnDefinition = "varchar(32) COMMENT '部门名称'")
    private String name;

    @Basic
    @Column(name = "code", columnDefinition = "varchar(32) COMMENT '编码'")
    private String code;

    @Basic
    @Column(name = "pid", columnDefinition = "varchar(32) COMMENT '父级ID'")
    private String pid;

    @Basic
    @Column(name = "tree_code", columnDefinition = "varchar(32) not null default '' COMMENT '树码(一级三位)'")
    private String treeCode;


}
