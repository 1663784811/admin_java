package com.cyyaw.store.table.activity.entity;


import com.cyyaw.jpa.util.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "a_coupon_user")
@org.hibernate.annotations.Table(appliesTo = "a_coupon_user", comment = "用户优惠券表")
public class ACouponUser implements BaseEntity<Integer>,  Serializable {
    private static final long serialVersionUID = 15177011723682985L;

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
    @Column(name = "code", columnDefinition = "varchar(32) not null COMMENT '优惠码'")
    private String code;

    @Basic
    @Column(name = "userid", columnDefinition = "varchar(32) COMMENT '用户u_user表ID'")
    private String userid;

    @Basic
    @Column(name = "acouponid", columnDefinition = "varchar(32) not null COMMENT '优惠券表a_coupon'")
    private String acouponid;

    @Basic
    @Column(name = "[status]",columnDefinition = "int COMMENT '状态{1:已领取,2:已使用,3:已转赠}'")
    private Integer status;
}
