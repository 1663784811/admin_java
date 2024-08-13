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
@Table(name = "a_coupon")
@org.hibernate.annotations.Table(appliesTo = "a_coupon", comment = "优惠券表")
public class ACoupon implements BaseEntity<Integer>,  Serializable {
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
    @Column(name = "createuserid", columnDefinition = "varchar(32) COMMENT '创建用户u_user表ID'")
    private String createuserid;


    @Basic
    @Column(name = "name", columnDefinition = "varchar(255) COMMENT '优惠券名'")
    private String name;

    @Basic
    @Column(name = "starttime", columnDefinition = "datetime COMMENT '开始时间'")

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date starttime;
    @Basic
    @Column(name = "endtime", columnDefinition = "datetime COMMENT '结束时间'")

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endtime;

    @Basic
    @Column(name = "type",columnDefinition = "int COMMENT '优惠券类型{0:通用}'")
    private Integer type;

    @Basic
    @Column(name = "algorithm",columnDefinition = "int default '0' COMMENT '结算算法{0:默认}'")
    private Integer algorithm;

    @Basic
    @Column(name = "[range]",columnDefinition = "int COMMENT '使用范围{0:门店,1:商场,2:通用}'")
    private Integer range;

    @Basic
    @Column(name = "rangetype",columnDefinition = "int COMMENT '使用范围类型{0:通用,1:商品,2:品类,3:品牌}'")
    private Integer rangetype;

}
