package com.cyyaw.signin.table.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "si_sign_log")
@org.hibernate.annotations.Table(appliesTo = "si_sign_log", comment = "签到记录表")
public class SiSignLog implements Serializable{

    private static final long serialVersionUID = 16672714648891L;

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
    @Column(name = "app_id", columnDefinition = "varchar(32) COMMENT '应用ID'")
    private String appId;

    @Basic
    @Column(name="sign_in_id", columnDefinition = "varchar(255) COMMENT '签到ID'")
    private String signInId;


    // ==================================================

    @Basic
    @Column(name="name", columnDefinition = "varchar(255) COMMENT '签到人'")
    private String name;

    @Basic
    @Column(name="phone", columnDefinition = "varchar(20) COMMENT '手机号'")
    private String phone;

    @Basic
    @Column(name="status", columnDefinition = "int COMMENT '状态{0:创建,1:确定,2:完成}'")
    private Integer status;

    @Basic
    @Column(name="other_sign", columnDefinition = "int COMMENT '是否帮签{0:否,1:是}'")
    private Integer otherSign;

    @Basic
    @Column(name="appoint", columnDefinition = "int COMMENT '是否指定{0:否,1:是}'")
    private Integer appoint;





}
