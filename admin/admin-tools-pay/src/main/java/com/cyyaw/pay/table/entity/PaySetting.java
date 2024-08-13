package com.cyyaw.pay.table.entity;

import com.cyyaw.jpa.util.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "pay_setting")
@org.hibernate.annotations.Table(appliesTo = "pay_setting", comment = "支付设置")
public class PaySetting implements BaseEntity<Integer>,  Serializable {
    private static final long serialVersionUID = 1568712426273933758L;

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
    @Column(name = "name", columnDefinition = "varchar(32) COMMENT '支付名称'")
    private String name;

    @Basic
    @Column(name = "pay_type", columnDefinition = "int COMMENT '最后付款方式{0:微信,1:支付宝}'")
    private Integer payType;

    @Basic
    @Column(name = "status", columnDefinition = "int COMMENT '状态{0:禁用,1:启用}'")
    private Integer status;

    //==============
    @Basic
    @Column(name = "pay_app_id", columnDefinition = "varchar(32) not null COMMENT '支付ID'")
    private String payAppId;


}
