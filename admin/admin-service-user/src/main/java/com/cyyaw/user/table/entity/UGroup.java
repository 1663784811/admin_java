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
@Table(name = "u_group")
@org.hibernate.annotations.Table(appliesTo = "u_group", comment = "用户群")
public class UGroup  implements BaseEntity<Integer>,  Serializable {

    private static final long serialVersionUID = 13663012723682985L;

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
    @Column(name = "name", columnDefinition = "varchar(255) not null COMMENT '群名称'")
    private String name;

    @Basic
    @Column(name = "logo", columnDefinition = "varchar(255) default '' COMMENT '群logo'")
    private String logo;

    @Basic
    @Column(name = "introduce", columnDefinition = "varchar(255) default '' COMMENT '群简介'")
    private String introduce;

    @Basic
    @Column(name = "address", columnDefinition = "varchar(255) default '' COMMENT '地址'")
    private String address;

    @Basic
    @Column(name = "type",columnDefinition = "int not null default '0' COMMENT '群类型{0:聊天,1:客服}'")
    private Integer type;


}
