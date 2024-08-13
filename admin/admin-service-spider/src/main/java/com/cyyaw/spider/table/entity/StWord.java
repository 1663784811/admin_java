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
@Table(name = "st_word", indexes = {@Index(columnList = "word")})
@org.hibernate.annotations.Table(appliesTo = "st_word", comment = "单词统计")
public class StWord implements BaseEntity<Integer>, Serializable {

    private static final long serialVersionUID = 157393283L;

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
    @Column(name = "word", columnDefinition = "varchar(255) COMMENT '单词'")
    private String word;

    @Basic
    @Column(name = "zh", columnDefinition = "varchar(255) COMMENT '中文'")
    private String zh;

    @Basic
    @Column(name = "num", columnDefinition = "int default '0' COMMENT '出现次数'")
    private Integer num;


}
