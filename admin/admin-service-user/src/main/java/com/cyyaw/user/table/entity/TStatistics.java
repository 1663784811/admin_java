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
@Table(name = "t_statistics")
@org.hibernate.annotations.Table(appliesTo = "t_statistics", comment = "统计表")
public class TStatistics implements BaseEntity<Integer>,  Serializable {
    private static final long serialVersionUID = 1568782627332870L;

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
    @Column(name = "adduser",columnDefinition = "int COMMENT '新增用户'")
    private Integer adduser;

    @Basic
    @Column(name = "man",columnDefinition = "int COMMENT '男生'")
    private Integer man;
    @Basic
    @Column(name = "women",columnDefinition = "int COMMENT '女生'")
    private Integer women;
    @Basic
    @Column(name = "[unknown]",columnDefinition = "int COMMENT '未知'")
    private Integer unknown;
    @Basic
    @Column(name = "userall",columnDefinition = "int COMMENT '所有用户'")
    private Integer userall;
    @Basic
    @Column(name = "visit",columnDefinition = "int COMMENT '今天访问量'")
    private Integer visit;
    @Basic
    @Column(name = "visitall",columnDefinition = "int COMMENT '总访问量'")
    private Integer visitall;
    @Basic
    @Column(name = "weixinuser",columnDefinition = "int COMMENT '微信用户'")
    private Integer weixinuser;

}
