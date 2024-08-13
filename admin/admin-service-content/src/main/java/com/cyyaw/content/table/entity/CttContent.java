package com.cyyaw.content.table.entity;

import com.cyyaw.jpa.util.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "ctt_content")
@org.hibernate.annotations.Table(appliesTo = "ctt_content", comment = "内容表")
public class CttContent implements BaseEntity<Integer>, Serializable {

    private static final long serialVersionUID = 158730117368285L;

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
    @Column(name = "app_id", columnDefinition = "varchar(32) COMMENT '应用ID'")
    private String appId;

    @Basic
    @Column(name = "user_id", columnDefinition = "varchar(32) not null COMMENT 'u_user用户表(当前用户)id'")
    private String userId;
    // =================================================================================
    @Basic
    @Column(name = "title", columnDefinition = "varchar(32) COMMENT '标题'")
    private String title;

    @Basic
    @Column(name = "content", columnDefinition = "text COMMENT '内容'")
    private String content;

    @Basic
    @Column(name = "status", columnDefinition = "int COMMENT '状态{0:全部可见,1:仅自己可见,2:主页可见}'")
    private Integer status;

    @Basic
    @Column(name = "label", columnDefinition = "varchar(255) COMMENT '标签'")
    private String label;

    @Basic
    @Column(name = "comment", columnDefinition = "int COMMENT '评论'")
    private Integer comment;

    @Basic
    @Column(name = "agree", columnDefinition = "int COMMENT '点赞'")
    private Integer agree;

}
