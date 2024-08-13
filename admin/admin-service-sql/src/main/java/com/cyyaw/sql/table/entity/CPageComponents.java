package com.cyyaw.sql.table.entity;

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
@Table(name = "c_page_components")
@org.hibernate.annotations.Table(appliesTo = "c_page_components", comment = "页面组件")
public class CPageComponents implements BaseEntity<Integer>, Serializable {

    private static final long serialVersionUID = 1667229296952725L;

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
    @Column(name = "page_id", columnDefinition = "varchar(45) COMMENT '页面ID'")
    private String pageId;
    @Basic
    @Column(name = "name", columnDefinition = "varchar(45) COMMENT '名称'")
    private String name;
    @Basic
    @Column(name = "components_code", columnDefinition = "varchar(45) COMMENT '组件编号'")
    private String componentsCode;
    @Basic
    @Column(name = "type", columnDefinition = "varchar(32) COMMENT '类型'")
    private String type;
    @Basic
    @Column(name = "data", columnDefinition = "text COMMENT '数据'")
    private String data;
    @Basic
    @Column(name = "icon", columnDefinition = "varchar(255) COMMENT 'icon图标'")
    private String icon;
    @Basic
    @Column(name = "sort", columnDefinition = "int COMMENT '排序'")
    private Integer sort;

    // ===  组件对象
    @Transient
    private List<CPageComponentsObj> objList;
}
