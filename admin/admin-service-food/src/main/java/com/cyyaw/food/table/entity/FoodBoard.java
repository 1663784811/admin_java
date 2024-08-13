package com.cyyaw.food.table.entity;

import com.cyyaw.jpa.util.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "food_board")
@org.hibernate.annotations.Table(appliesTo = "food_board", comment = "桌子")
public class FoodBoard implements BaseEntity<Integer>,  Serializable {
    private static final long serialVersionUID = 158730117368985L;

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
    @Column(name = "store_id", columnDefinition = "varchar(32) COMMENT '所属门店e_storeid表ID'")
    private String storeId;

    // =================================================================================

    @Basic
    @Column(name = "name", columnDefinition = "varchar(32) COMMENT '名称'")
    private String name;

    @Basic
    @Column(name = "status", columnDefinition = "int COMMENT '状态{0:空闲,1:占用,2:预定}'")
    private Integer status;


    @Basic
    @Column(name = "number", columnDefinition = "int COMMENT '人数'")
    private Integer number;


}
