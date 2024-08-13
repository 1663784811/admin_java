package com.cyyaw.sql.table.entity;

import com.cyyaw.jpa.util.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "c_page_components_obj")
@org.hibernate.annotations.Table(appliesTo = "c_page_components_obj", comment = "页面组件对象")
public class CPageComponentsObj implements BaseEntity<Integer>,  Serializable {

    private static final long serialVersionUID = 167229296952725L;

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
    @Column(name = "page_components_id", columnDefinition = "varchar(32) COMMENT '组件ID'")
    private String pageComponentsId;

    @Basic
    @Column(name = "name", columnDefinition = "varchar(32) COMMENT '名称'")
    private String name;
    @Basic
    @Column(name = "data_key", columnDefinition = "varchar(32) COMMENT '数据Key'")
    private String dataKey;
    @Basic
    @Column(name = "data", columnDefinition = "text COMMENT '数据'")
    private String data;
    @Basic
    @Column(name = "sort", columnDefinition = "int COMMENT '排序'")
    private Integer sort;
}
