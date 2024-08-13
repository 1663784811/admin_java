package com.cyyaw.spider.table.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "tag")
@org.hibernate.annotations.Table(appliesTo = "tag", comment = "标签")
public class Tag implements Serializable{
    private static final long serialVersionUID = 1667271465352725L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic
    @Column(name="id", columnDefinition = "int auto_increment not null COMMENT 'id'")
    private Integer id;
    @Basic
    @Column(name="create_time", columnDefinition = "datetime COMMENT '创建时间'")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @Basic
    @Column(name="del", columnDefinition = "int default '0' COMMENT '是否删除{0:否,1:是}'")
    private Integer del;
    @Basic
    @Column(name="note", columnDefinition = "varchar(255) default '' COMMENT '备注'")
    private String note;
    @Basic
    @Column(name="tid", columnDefinition = "varchar(32) default '' not null COMMENT 'tid'")
    private String tid;


    @Basic
    @Column(name="name", columnDefinition = "varchar(255) COMMENT '标签名'")
    private String name;
    @Basic
    @Column(name="pid", columnDefinition = "varchar(32) COMMENT '父节点ID'")
    private String pid;
    @Basic
    @Column(name="treecode", columnDefinition = "varchar(255) COMMENT '树码'")
    private String treecode;
    @Basic
    @Column(name="type", columnDefinition = "int COMMENT '地区类型{1:公司,2:人物,3:昵称}'")
    private Integer type;
}
