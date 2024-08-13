package com.cyyaw.web.table.entity;

import com.cyyaw.jpa.util.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "web_image")
@org.hibernate.annotations.Table(appliesTo = "web_image", comment = "影像")
public class WebImage implements BaseEntity<Integer>,  Serializable {
    private static final long serialVersionUID = 1387301173682985L;

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
    @Column(name = "web_image_type_id", columnDefinition = "varchar(32) COMMENT '影像分类ID'")
    private String webImageTypeId;

    // ================================================================================

    @Basic
    @Column(name = "path", columnDefinition = "varchar(255) COMMENT '路径'")
    private String path;
    @Basic
    @Column(name = "name", columnDefinition = "varchar(255) COMMENT '影像名'")
    private String name;

    @Basic
    @Column(name = "type", columnDefinition = "int COMMENT '影像类型'")
    private Integer type;

}
