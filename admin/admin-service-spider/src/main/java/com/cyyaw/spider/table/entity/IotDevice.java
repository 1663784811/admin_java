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
@Table(name = "iot_device")
@org.hibernate.annotations.Table(appliesTo = "iot_device", comment = "设备")
public class IotDevice implements BaseEntity<Integer>, Serializable {

    private static final long serialVersionUID = 1573669328L;

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
    @Column(name = "name", columnDefinition = "varchar(255) COMMENT '设备名称'")
    private String name;

    @Basic
    @Column(name = "type", columnDefinition = "int COMMENT '类型{1:车辆}'")
    private Integer type;

    @Basic
    @Column(name = "[describe]", columnDefinition = "text COMMENT '描述'")
    private String describe;

}