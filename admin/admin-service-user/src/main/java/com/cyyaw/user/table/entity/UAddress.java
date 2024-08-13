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
@Table(name = "u_address")
@org.hibernate.annotations.Table(appliesTo = "u_address", comment = "用户地址")
public class UAddress  implements BaseEntity<Integer>,  Serializable {
    private static final long serialVersionUID = 1587301172382985L;

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
    @Column(name = "user_id", columnDefinition = "varchar(32) not null COMMENT 'u_user用户表id'")
    private String userId;

    @Basic
    @Column(name = "province",  columnDefinition = "varchar(32) COMMENT '省份'")
    private String province;
    @Basic
    @Column(name = "city", columnDefinition = "varchar(32) COMMENT '城市'")
    private String city;
    @Basic
    @Column(name = "district",  columnDefinition = "varchar(32) COMMENT '地区'")
    private String district;
    @Basic
    @Column(name = "address", columnDefinition = "varchar(255) COMMENT '地址'")
    private String address;

    @Basic
    @Column(name = "phone",  columnDefinition = "varchar(15) COMMENT '手机号'")
    private String phone;

    @Basic
    @Column(name = "name",  columnDefinition = "varchar(32) COMMENT '姓名'")
    private String name;

    @Basic
    @Column(name = "default_is", columnDefinition = "int COMMENT '是否默认{0:否,1:是}'")
    private Integer defaultIs;


}
