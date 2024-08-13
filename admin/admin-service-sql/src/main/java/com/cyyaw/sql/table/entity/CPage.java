package com.cyyaw.sql.table.entity;

import com.cyyaw.jpa.util.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Data
@Entity
@Table(name = "c_page")
@org.hibernate.annotations.Table(appliesTo = "c_page", comment = "页面")
public class CPage implements BaseEntity<Integer>,  Serializable {

    private static final long serialVersionUID = 166582321135876L;

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
    @Column(name = "page_icon", columnDefinition = "text COMMENT '图标'")
    private String pageIcon;

    @Basic
    @Column(name = "page_code", columnDefinition = "varchar(32) not null COMMENT 'pageCode'")
    private String pageCode;


    @Basic
    @Column(name = "type", columnDefinition = "int default '0' COMMENT '页面类型{0:企业,1:APP管理,2:门店管理}'")
    private Integer type;


    // ==================================================
    // 组件列表
    @Transient
    private List<CPageComponents> components;

}
