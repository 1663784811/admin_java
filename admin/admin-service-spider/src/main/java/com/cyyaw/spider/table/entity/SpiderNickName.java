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
@Table(name = "spider_nick_name")
@org.hibernate.annotations.Table(appliesTo = "spider_nick_name", comment = "昵称")
public class SpiderNickName implements BaseEntity<Integer>,  Serializable {

    private static final long serialVersionUID = 157693283L;

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
    @Column(name = "host", columnDefinition = "varchar(255) COMMENT '来源域名'")
    private String host;

    @Basic
    @Column(name = "url", columnDefinition = "text COMMENT 'url'")
    private String url;

    @Basic
    @Column(name = "nick_name", columnDefinition = "varchar(255) COMMENT '昵称'")
    private String nickName;

    @Basic
    @Column(name = "face", columnDefinition = "text COMMENT '头像'")
    private String face;

    @Basic
    @Column(name = "account", columnDefinition = "varchar(255) COMMENT '账号'")
    private String account;

    @Basic
    @Column(name = "source", columnDefinition = "int default '0' COMMENT '来源{1:网页,2:小红书}'")
    private Integer source;

    @Basic
    @Column(name = "gender", columnDefinition = "int default '0' COMMENT '来源{0:未知,1:男,2:女}'")
    private Integer gender;

    @Basic
    @Column(name = "follow", columnDefinition = "int default '0' COMMENT '关注数'")
    private Integer follow;

    @Basic
    @Column(name = "fans", columnDefinition = "int default '0' COMMENT '粉丝数'")
    private Integer fans;

    @Basic
    @Column(name = "like_num", columnDefinition = "int default '0' COMMENT '点赞'")
    private Integer likeNum;

    @Basic
    @Column(name = "address", columnDefinition = "varchar(255) COMMENT '地址'")
    private String address;

    @Basic
    @Column(name = "company", columnDefinition = "varchar(255) COMMENT '公司'")
    private String company;

    @Basic
    @Column(name = "content", columnDefinition = "text COMMENT '内容'")
    private String content;

    @Basic
    @Column(name = "href", columnDefinition = "text COMMENT 'href'")
    private String href;


}
