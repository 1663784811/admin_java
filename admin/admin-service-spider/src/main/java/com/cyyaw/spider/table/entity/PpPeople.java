package com.cyyaw.spider.table.entity;


import com.cyyaw.jpa.util.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "pp_people")
@org.hibernate.annotations.Table(appliesTo = "pp_people", comment = "人")
public class PpPeople implements BaseEntity<Integer>,  Serializable {

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
    @Column(name = "name", columnDefinition = "varchar(255) COMMENT '姓名'")
    private String name;

    @Basic
    @Column(name = "gender", columnDefinition = "varchar(5) COMMENT '性别'")
    private String gender;

    @Basic
    @Column(name = "birthday", columnDefinition = "datetime COMMENT '生日'")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date birthday;

    @Basic
    @Column(name = "face", columnDefinition = "varchar(255) COMMENT '头像'")
    private String face;

    @Basic
    @Column(name = "follow", columnDefinition = "int COMMENT '关注'")
    private Integer follow;

    @Basic
    @Column(name = "fans", columnDefinition = "int COMMENT '关注'")
    private Integer fans;

    @Basic
    @Column(name = "[describe]", columnDefinition = "text COMMENT '描述'")
    private String describe;

    @Basic
    @Column(name = "email", columnDefinition = "varchar(255) COMMENT '邮箱'")
    private String email;


}
