package com.cyyaw.wixin.table.entity;


import com.cyyaw.jpa.util.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "weixin_miniapp_setting")
@org.hibernate.annotations.Table(appliesTo = "weixin_miniapp_setting", comment = "微信小程序设置")
public class WeixinMiniappSetting implements BaseEntity<Integer>,  Serializable {
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


    // =================================================================================
    @Basic
    @Column(name = "name", columnDefinition = "varchar(255) COMMENT '名称'")
    private String name;
    @Basic
    @Column(name = "mini_app_id", columnDefinition = "varchar(255) COMMENT '微信小程序ID'")
    private String miniAppId;
    @Basic
    @Column(name = "secret", columnDefinition = "varchar(255) COMMENT '微信小程序密钥'")
    private String secret;
    @Basic
    @Column(name = "token", columnDefinition = "varchar(255) COMMENT '微信小程序token'")
    private String token;
    @Basic
    @Column(name = "aesKey", columnDefinition = "varchar(255) COMMENT '微信小程序aesKey'")
    private String aesKey;
    @Basic
    @Column(name = "msg_data_format", columnDefinition = "varchar(255) COMMENT '消息格式，XML或者JSON'")
    private String msgDataFormat;


    @Basic
    @Column(name = "status", columnDefinition = "int default '1' COMMENT '是否启用{0:否,1:是}'")
    private Integer status;

}






















