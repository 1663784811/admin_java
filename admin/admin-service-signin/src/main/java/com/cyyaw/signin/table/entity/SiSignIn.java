package com.cyyaw.signin.table.entity;

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
@Table(name = "si_sign_in")
@org.hibernate.annotations.Table(appliesTo = "si_sign_in", comment = "签到表")
public class SiSignIn implements BaseEntity<Integer>,  Serializable {

    private static final long serialVersionUID = 166727146408891L;

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

    // ==================================================

    @Basic
    @Column(name = "title", columnDefinition = "varchar(255) COMMENT '标题'")
    private String title;


    @Basic
    @Column(name = "introduction", columnDefinition = "text COMMENT '简介'")
    private String introduction;

    @Basic
    @Column(name = "tips", columnDefinition = "varchar(255) COMMENT '温馨提示'")
    private String tips;

    @Basic
    @Column(name = "status", columnDefinition = "int COMMENT '状态{0:创建,1:进行中,2:完成}'")
    private Integer status;

    @Basic
    @Column(name = "start_time", columnDefinition = "datetime COMMENT '开始时间'")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @Basic
    @Column(name = "end_time", columnDefinition = "datetime COMMENT '结束时间'")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    @Basic
    @Column(name = "sign_ed", columnDefinition = "int default '0' COMMENT '已签到人数'")
    private Integer signEd;

    @Basic
    @Column(name = "sign_ing", columnDefinition = "int default '0' COMMENT '未签到人数'")
    private Integer signIng;

    @Transient
    private List<SiSignLog> signLogList;

}
