package com.cyyaw.equipment.table.entity;


import com.cyyaw.jpa.util.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "eq_equipment")
@org.hibernate.annotations.Table(appliesTo = "eq_equipment", comment = "设备")
public class EqEquipment implements BaseEntity<Integer>, Serializable {
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
    @Column(name = "code", columnDefinition = "varchar(255) COMMENT '设备编号'")
    private String code;

    @Basic
    @Column(name = "name", columnDefinition = "varchar(255) COMMENT '设备名称'")
    private String name;
    @Basic
    @Column(name = "status", columnDefinition = "int default '0' COMMENT '状态{0:离线,1:在线}'")
    private Integer status;

    @Basic
    @Column(name = "type", columnDefinition = "int default '0' COMMENT '设备类型{0:未知,1:手机APP}'")
    private Integer type;


    @Basic
    @Column(name = "df_account", columnDefinition = "varchar(32) default '' COMMENT '默认账号'")
    private String dfAccount;

    @Basic
    @Column(name = "df_pwd", columnDefinition = "varchar(32) default '' COMMENT '默认密码'")
    private String dfPwd;


    @Basic
    @Column(name = "account", columnDefinition = "varchar(32) default '' COMMENT '账号'")
    private String account;

    @Basic
    @Column(name = "pwd", columnDefinition = "varchar(32) default '' COMMENT '密码'")
    private String pwd;


    @Basic
    @Column(name = "producer_code", columnDefinition = "varchar(64) default '' COMMENT '厂家编号'")
    private String producerCode;


    @Basic
    @Column(name = "device_asset_code", columnDefinition = "varchar(64) default '' COMMENT '设备资产编码'")
    private String deviceAssetCode;


    @Basic
    @Column(name = "software_version", columnDefinition = "varchar(32) default '' COMMENT '系统版本号'")
    private String softwareVersion;

    @Basic
    @Column(name = "protocol_version", columnDefinition = "varchar(32) default '' COMMENT '协议版本'")
    private String protocolVersion;

    @Basic
    @Column(name = "cir_in_Place_num", columnDefinition = "varchar(32) default '' COMMENT '模块在位数量'")
    private String cirInPlaceNum;



}
