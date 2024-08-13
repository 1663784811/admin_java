package com.cyyaw.store.table.order.entity;

import com.cyyaw.jpa.util.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "o_discount")
@org.hibernate.annotations.Table(appliesTo = "o_discount", comment = "订单优惠表")
public class ODiscount implements BaseEntity<Integer>,  Serializable {
    private static final long serialVersionUID = 156873426273133758L;

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
    @Column(name = "order_id", columnDefinition = "varchar(32) not null COMMENT 'o_order订单表ID'")
    private String orderId;
    @Basic
    @Column(name = "other_id", columnDefinition = "varchar(32) COMMENT '附ID'")
    private String otherId;
    // =================================================================================


    @Basic
    @Column(name = "type", columnDefinition = "int not null default '0' COMMENT '优惠类型{0:优惠金额,1:赠送积分,2:优惠券}'")
    private Integer type;

    @Basic
    @Column(name="money", columnDefinition = "decimal(18,2) not null default '0' COMMENT '优惠额度'")
    private BigDecimal money;

    @Basic
    @Column(name = "description", columnDefinition = "varchar(255) COMMENT '优惠描述'")
    private String description;

}
