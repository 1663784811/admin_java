package com.cyyaw.user.table.entity;

import com.cyyaw.jpa.util.entity.TreeEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "t_power")
@org.hibernate.annotations.Table(appliesTo = "t_power", comment = "权限表")
public class TPower implements TreeEntity<Integer>, Serializable {
    private static final long serialVersionUID = 1568782627393758L;

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
    @Column(name = "enterprise_code", columnDefinition = "varchar(32) COMMENT '所属企业e_enterprise表code'")
    private String enterpriseCode;


    // =================================================================================

    @Basic
    @Column(name = "pid", columnDefinition = "varchar(32) COMMENT '父级ID'")
    private String pid;
    @Basic
    @Column(name = "tree_code", columnDefinition = "varchar(32) default '' COMMENT '树码(一级三位)'")
    private String treeCode;

    @Basic
    @Column(name = "code", columnDefinition = "varchar(255) COMMENT '受权码'")
    private String code;
    @Basic
    @Column(name = "icon", columnDefinition = "varchar(255) COMMENT '图标'")
    private String icon;
    @Basic
    @Column(name = "name", columnDefinition = "varchar(32) COMMENT '名称'")
    private String name;
    @Basic
    @Column(name = "power_type", columnDefinition = "int COMMENT '权限类型{1:菜单,2:按钮}'")
    private Integer powerType;
    @Basic
    @Column(name = "status", columnDefinition = "int COMMENT '状态{1:显示,0:隐藏}'")
    private Integer status;
    @Basic
    @Column(name = "is_power", columnDefinition = "int default '0' COMMENT '是否受权限控制{1:是,0:否}'")
    private Integer isPower;
    @Basic
    @Column(name = "url", columnDefinition = "varchar(255) COMMENT 'url'")
    private String url;
    @Basic
    @Column(name = "route_name", columnDefinition = "varchar(255) COMMENT '路由'")
    private String routeName;
    @Basic
    @Column(name = "sort", columnDefinition = "int default '1' COMMENT '排序'")
    private Integer sort;

    @Basic
    @Column(name = "user_type", columnDefinition = "int default '0' COMMENT '用户类型{0:企业,1:APP,2:门店}'")
    private Integer userType;

}
