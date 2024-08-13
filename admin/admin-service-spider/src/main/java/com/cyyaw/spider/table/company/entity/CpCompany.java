package com.cyyaw.spider.table.company.entity;

import com.cyyaw.jpa.util.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "cp_company")
@org.hibernate.annotations.Table(appliesTo = "cp_company", comment = "公司")
public class CpCompany implements BaseEntity<Integer>,  Serializable {

    private static final long serialVersionUID = 1573661935283L;

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
    @Column(name = "name", columnDefinition = "varchar(255) COMMENT '公司名称'")
    private String name;

    @Basic
    @Column(name = "industry", columnDefinition = "varchar(255) COMMENT '行业'")
    private String industry;

    @Basic
    @Column(name = "establish_time", columnDefinition = "datetime COMMENT '成立时间'")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date establishTime;


    @Basic
    @Column(name = "legal_person", columnDefinition = "varchar(255) COMMENT '法人'")
    private String legalPerson;

    @Basic
    @Column(name = "person", columnDefinition = "text COMMENT '主要要员'")
    private String person;


    @Basic
    @Column(name = "stock_type", columnDefinition = "int COMMENT '地区类型{1:A股,2:港股,3:美股}'")
    private Integer stockType;

    @Basic
    @Column(name = "stock_name", columnDefinition = "varchar(255) COMMENT '股票名称'")
    private String stockName;

    @Basic
    @Column(name = "stock_no", columnDefinition = "varchar(32) COMMENT '证券代码'")
    private String stockNo;


    @Transient
    private List<CpRecruit> recruitList;
}
