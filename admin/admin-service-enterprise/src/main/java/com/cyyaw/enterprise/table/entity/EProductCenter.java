package com.cyyaw.enterprise.table.entity;


import com.cyyaw.jpa.util.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "e_product_center")
@org.hibernate.annotations.Table(appliesTo = "e_product_center", comment = "产品中心")
public class EProductCenter implements BaseEntity<Integer>,  Serializable {
    private static final long serialVersionUID = 15687262756870L;

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
    @Column(name = "name", columnDefinition = "varchar(255) COMMENT '产品名称'")
    private String name;

    @Basic
    @Column(name = "logo", columnDefinition = "varchar(255) COMMENT 'logo图片'")
    private String logo;

    @Basic
    @Column(name = "introduction", columnDefinition = "varchar(255) COMMENT '产品简介'")
    private String introduction;

    @Basic
    @Column(name = "type_code", columnDefinition = "varchar(255) COMMENT '类型'")
    private String typeCode;
}






















