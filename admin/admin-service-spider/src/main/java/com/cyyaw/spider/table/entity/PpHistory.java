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
@Table(name = "pp_history")
@org.hibernate.annotations.Table(appliesTo = "pp_history", comment = "人历史")
public class PpHistory implements BaseEntity<Integer>,  Serializable {

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
    @Column(name = "pp_id", columnDefinition = "varchar(32) COMMENT '人ID'")
    private String ppId;

    @Basic
    @Column(name = "history_time", columnDefinition = "datetime COMMENT '时间'")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date historyTime;

    @Basic
    @Column(name = "pp_describe", columnDefinition = "text COMMENT '描述'")
    private String ppDescribe;

    @Basic
    @Column(name = "analysis", columnDefinition = "text COMMENT '分析'")
    private String analysis;


}
