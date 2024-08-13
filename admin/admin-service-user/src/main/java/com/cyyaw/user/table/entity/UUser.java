package com.cyyaw.user.table.entity;


import com.cyyaw.jpa.util.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "u_user")
@org.hibernate.annotations.Table(appliesTo = "u_user", comment = "用户表")
public class UUser implements BaseEntity<Integer>,  Serializable {
    private static final long serialVersionUID = 15873011723682985L;

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
    @Column(name = "app_id", columnDefinition = "varchar(32) COMMENT '应用ID'")
    private String appId;
    // =================================================================================

    @Basic
    @Column(name = "account", columnDefinition = "varchar(32) COMMENT '账号'")
    private String account;
    @Basic
    @Column(name = "password", columnDefinition = "varchar(32) COMMENT '密码'")
    private String password;
    @Basic
    @Column(name = "true_name", columnDefinition = "varchar(32) COMMENT '真实姓名'")
    private String trueName;
    @Basic
    @Column(name = "phone", columnDefinition = "varchar(15) COMMENT '手机号'")
    private String phone;
    @Basic
    @Column(name = "nick_name", columnDefinition = "varchar(32) COMMENT '昵称'")
    private String nickName;
    @Basic
    @Column(name = "face", columnDefinition = "text COMMENT '用户头像'")
    private String face;

    @Basic
    @Column(name = "sex", columnDefinition = "varchar(5) COMMENT '性别'")
    private String sex;

    @Basic
    @Column(name = "can_login_time", columnDefinition = "datetime COMMENT '可登录时间'")

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date canLoginTime;
    @Basic
    @Column(name = "email", columnDefinition = "varchar(255) COMMENT '邮箱'")
    private String email;
    @Basic
    @Column(name = "ip", columnDefinition = "varchar(60) COMMENT '最后登录IP'")
    private String ip;
    @Basic
    @Column(name = "last_login_time", columnDefinition = "datetime COMMENT '最后登录时间'")

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastLoginTime;
    @Basic
    @Column(name = "salt", columnDefinition = "varchar(32) COMMENT '加密盐'")
    private String salt;
    @Basic
    @Column(name = "status", columnDefinition = "int COMMENT '状态{0:正常,1:暂时锁定,2:永久锁定}'")
    private Integer status;

    @Basic
    @Column(name = "type", columnDefinition = "int COMMENT '会员类型{0:普通会员,1:客服}'")
    private Integer type;

    @Basic
    @Column(name = "admin_id", columnDefinition = "varchar(32) COMMENT '客服t_admin表id'")
    private String adminId;


    @Basic
    @Column(name = "balance", columnDefinition = "decimal(18,2) COMMENT '余额'")
    private BigDecimal balance;

    @Basic
    @Column(name = "integral", columnDefinition = "int COMMENT '积分'")
    private Integer integral;

    @Basic
    @Column(name = "open_id", columnDefinition = "varchar(64) COMMENT '微信openid'")
    private String openId;

    @Basic
    @Column(name = "union_id", columnDefinition = "varchar(64) COMMENT '微信unionid'")
    private String unionId;

    @Basic
    @Column(name = "introduce_sign", columnDefinition = "varchar(255) COMMENT '个性签名'")
    private String introduceSign;

}
