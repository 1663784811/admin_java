package com.cyyaw.spider.table.company.entity;

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
@Table(name = "cp_recruit")
@org.hibernate.annotations.Table(appliesTo = "cp_recruit", comment = "公司招聘")
public class CpRecruit implements BaseEntity<Integer>, Serializable {

    private static final long serialVersionUID = 15736619323L;

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
    @Column(name = "cp_id", columnDefinition = "varchar(32) COMMENT '公司ID'")
    private String cpId;

    // ==================================================
    @Basic
    @Column(name = "name", columnDefinition = "varchar(255) COMMENT '职位名'")
    private String name;

    @Basic
    @Column(name = "tag", columnDefinition = "varchar(255) COMMENT '标签'")
    private String tag;

    @Basic
    @Column(name = "address", columnDefinition = "varchar(255) COMMENT '地址'")
    private String address;

    @Basic
    @Column(name = "hr", columnDefinition = "varchar(255) COMMENT 'hr'")
    private String hr;

    @Basic
    @Column(name = "max_price", columnDefinition = "decimal(18,2)  default '0' COMMENT '最高工资'")
    private BigDecimal maxPrice;

    @Basic
    @Column(name = "min_price", columnDefinition = "decimal(18,2)  default '0' COMMENT '最低工资'")
    private BigDecimal minPrice;

    @Basic
    @Column(name = "demand", columnDefinition = "text COMMENT '需求'")
    private String demand;

    @Basic
    @Column(name = "resource", columnDefinition = "varchar(255) COMMENT '来源'")
    private String resource;

    @Basic
    @Column(name = "activity", columnDefinition = "varchar(255) COMMENT '活跃'")
    private String activity;

}
