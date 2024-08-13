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
@Table(name = "sp_school")
@org.hibernate.annotations.Table(appliesTo = "sp_school", comment = "学校")
public class SpSchool implements BaseEntity<Integer>, Serializable {

    private static final long serialVersionUID = 15736693283L;

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
    @Column(name = "name", columnDefinition = "varchar(255) default '' COMMENT '学校名'")
    private String name;

    @Basic
    @Column(name = "address", columnDefinition = "varchar(255) default '' COMMENT '地址'")
    private String address;

    @Basic
    @Column(name = "longitude", columnDefinition = "varchar(32) default '' COMMENT '经度'")
    private String longitude;

    @Basic
    @Column(name = "latitude", columnDefinition = "varchar(32) default '' COMMENT '纬度'")
    private String latitude;

    @Basic
    @Column(name = "level", columnDefinition = "varchar(32) default '' COMMENT '级别{1:小学,2:初中,3:高中,4:大学}'")
    private String level;

}