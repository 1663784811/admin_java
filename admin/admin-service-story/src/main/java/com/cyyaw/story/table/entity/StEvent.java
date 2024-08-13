package com.cyyaw.story.table.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "st_event")
@org.hibernate.annotations.Table(appliesTo = "st_event", comment = "故事事件表")
public class StEvent implements Serializable{

    private static final long serialVersionUID = 166727648891L;

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
    @Column(name = "app_id", columnDefinition = "varchar(32) COMMENT '应用ID'")
    private String appId;

    @Basic
    @Column(name="story_id", columnDefinition = "varchar(255) COMMENT '故事ID'")
    private String storyId;


    // ==================================================

    @Basic
    @Column(name = "event_time", columnDefinition = "varchar(255) COMMENT '事件时间'")
    private String eventTime;

    @Basic
    @Column(name="event", columnDefinition = "varchar(255) COMMENT '事件'")
    private String event;

    @Basic
    @Column(name="details", columnDefinition = "text COMMENT '详情'")
    private String details;






}
