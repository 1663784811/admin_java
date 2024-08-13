package com.cyyaw.store.table.goods.entity;

import com.cyyaw.jpa.util.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "g_brand")
@org.hibernate.annotations.Table(appliesTo = "g_brand", comment = "品牌表")
public class GBrand implements BaseEntity<Integer>, Serializable {
    private static final long serialVersionUID = 15687826272233758L;

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
    @Column(name = "name", columnDefinition = "varchar(32) COMMENT '品牌名称'")
    private String name;
    @Basic
    @Column(name = "img", columnDefinition = "varchar(255) COMMENT '图标'")
    private String img;

    @Basic
    @Column(name = "status", columnDefinition = "int COMMENT '状态{1:显示,0:隐藏}'")
    private Integer status;

    @Basic
    @Column(name = "sort", columnDefinition = "int default '1' COMMENT '排序'")
    private Integer sort;

}
