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
@Table(name = "e_application")
@org.hibernate.annotations.Table(appliesTo = "e_application", comment = "企业应用表")
public class EApplication implements BaseEntity<Integer>, Serializable {
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
    @Column(name = "enterprise_code", columnDefinition = "varchar(32) COMMENT '所属企业e_enterprise表code'")
    private String enterpriseCode;

    @Basic
    @Column(name = "code", columnDefinition = "varchar(32) unique not null COMMENT '应用编号'")
    private String code;
    // =================================================================================

    @Basic
    @Column(name = "name", columnDefinition = "varchar(255) COMMENT '应用名称'")
    private String name;
    @Basic
    @Column(name = "url", columnDefinition = "varchar(255) COMMENT 'url地址'")
    private String url;
    @Basic
    @Column(name = "logo", columnDefinition = "varchar(255) COMMENT 'logo图片'")
    private String logo;
    @Basic
    @Column(name = "type_code", columnDefinition = "varchar(255) COMMENT '类型'")
    private String typeCode;
}






















