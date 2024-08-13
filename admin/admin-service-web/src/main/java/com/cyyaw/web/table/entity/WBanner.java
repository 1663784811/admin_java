package com.cyyaw.web.table.entity;


import com.cyyaw.jpa.util.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "w_banner")
@org.hibernate.annotations.Table(appliesTo = "w_banner", comment = "首页banner图")
public class WBanner implements BaseEntity<Integer>,  Serializable {
    private static final long serialVersionUID = 1568784262756870L;

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
    @Column(name = "name", columnDefinition = "varchar(255) COMMENT '名称'")
    private String name;
    @Basic
    @Column(name = "url", columnDefinition = "varchar(255) COMMENT '跳转url'")
    private String url;
    @Basic
    @Column(name = "img", columnDefinition = "varchar(255) COMMENT '图片地址'")
    private String img;
    @Basic
    @Column(name = "is_show", columnDefinition = "int default '0' COMMENT '是否显示{0:否,1:是}'")
    private Integer isShow;
    @Basic
    @Column(name = "position", columnDefinition = "int default '0' COMMENT '显示位置{1:首页}'")
    private Integer position;
}






















