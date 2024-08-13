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
@Table(name = "a_coupon_code")
@org.hibernate.annotations.Table(appliesTo = "a_coupon_code", comment = "优惠码表")
public class ACouponCode implements BaseEntity<Integer>,  Serializable {
    private static final long serialVersionUID = 15170011723682985L;

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
    @Column(name = "[status]",columnDefinition = "int COMMENT '状态{0:未领取,1:已领取,2:已使用}'")
    private Integer status;

    @Basic
    @Column(name = "acouponid", columnDefinition = "varchar(32) not null COMMENT '优惠券表a_coupon'")
    private String acouponid;

}
