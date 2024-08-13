package com.cyyaw.spider.table.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "tag_link")
@org.hibernate.annotations.Table(appliesTo = "tag_link", comment = "标签关联")
public class TagLink implements Serializable{
    private static final long serialVersionUID = 1667271465383838L;
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
    @Column(name="link_id", columnDefinition = "varchar(32) not null COMMENT '关联ID'")
    private String linkId;
    @Basic
    @Column(name="tag_id", columnDefinition = "varchar(32) not null COMMENT '标签ID'")
    private String tagId;
}
