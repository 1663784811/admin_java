package com.cyyaw.user.table.entity;


import com.cyyaw.jpa.util.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "t_message")
@org.hibernate.annotations.Table(appliesTo = "t_message", comment = "消息表")
public class TMessage implements BaseEntity<Integer>, Serializable {
    private static final long serialVersionUID = 1568782627345202L;

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
    @Column(name = "author", columnDefinition = "varchar(32) COMMENT '作者'")
    private String author;
    @Basic
    @Column(name = "content", columnDefinition = "varchar(255) COMMENT '内容'")
    private String content;
    @Basic
    @Column(name = "mssagetype", columnDefinition = "int COMMENT '消息类型'")
    private Integer mssagetype;

    @Basic
    @Column(name = "orginal", columnDefinition = "int COMMENT '是否原创{0:否,1:是}'")
    private Integer orginal;
    @Basic
    @Column(name = "publishtime", columnDefinition = "datetime COMMENT '发布时间'")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date publishtime;
    @Basic
    @Column(name = "status", columnDefinition = "int COMMENT '状态'")
    private Integer status;
    @Basic
    @Column(name = "title", columnDefinition = "varchar(255) COMMENT '标题'")
    private String title;
    @Basic
    @Column(name = "url", columnDefinition = "varchar(32) COMMENT 'url'")
    private String url;
}
