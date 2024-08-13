package com.cyyaw.store.table.order.entity;

import com.cyyaw.jpa.util.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "o_order")
@org.hibernate.annotations.Table(appliesTo = "o_order", comment = "订单表")
public class OOrder implements BaseEntity<Integer>,  Serializable {
    private static final long serialVersionUID = 1568712426273933758L;

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

    // ===============================================================================================================================
    @Basic
    @Column(name = "app_id", columnDefinition = "varchar(32) COMMENT '应用ID'")
    private String appId;
    @Basic
    @Column(name = "user_id", columnDefinition = "varchar(32) COMMENT 'u_user用户表(当前用户)id'")
    private String userId;
    @Basic
    @Column(name = "board_id", columnDefinition = "varchar(32) COMMENT '餐桌Id'")
    private String boardId;
    @Basic
    @Column(name = "enterprise_code", columnDefinition = "varchar(32) COMMENT '所属企业e_enterprise表code'")
    private String enterpriseCode;
    @Basic
    @Column(name = "store_id", columnDefinition = "varchar(32) COMMENT '所属门店e_storeid表ID'")
    private String storeId;
    // ===============================================================================================================================

    @Basic
    @Column(name = "user_name", columnDefinition = "varchar(32) COMMENT '用户名'")
    private String userName;
    @Basic
    @Column(name = "store_name", columnDefinition = "varchar(32) COMMENT '门店名'")
    private String storeName;

    //==============
    @Basic
    @Column(name = "order_no", columnDefinition = "varchar(32) not null COMMENT '订单号'")
    private String orderNo;

    @Basic
    @Column(name = "type", columnDefinition = "int not null default '0' COMMENT '订单类型{0:普通订单,1:组合订单}'")
    private Integer type;

    @Basic
    @Column(name = "status", columnDefinition = "varchar(32) COMMENT '订单状态{0:待付款,1:待发货,2:已发货,3:待确认,4:完成}'")
    private Integer status;

    @Basic
    @Column(name = "address_id", columnDefinition = "varchar(32) COMMENT '地址ID'")
    private String addressId;
    @Basic
    @Column(name = "address_detail", columnDefinition = "varchar(32) COMMENT '地址详情'")
    private String addressDetail;
    @Basic
    @Column(name = "phone", columnDefinition = "varchar(32) COMMENT '手机号'")
    private String phone;

    @Basic
    @Column(name = "description", columnDefinition = "varchar(255) COMMENT '描述'")
    private String description;

    @Basic
    @Column(name = "number", columnDefinition = "int  default '0' COMMENT '商品总数量'")
    private Integer number;



    @Basic
    @Column(name = "amount", columnDefinition = "decimal(18,2)  default '0' COMMENT '商品总价格(未加其它费用)'")
    private BigDecimal amount;
    @Basic
    @Column(name = "express_price", columnDefinition = "decimal(18,2)  default '0' COMMENT '快递费用'")
    private BigDecimal expressPrice;
    @Basic
    @Column(name = "payable_amount", columnDefinition = "decimal(18,2) COMMENT '最后实际应付(计算所有优惠和费用后)'")
    private BigDecimal payableAmount;



    @Basic
    @Column(name = "pay_type", columnDefinition = "int COMMENT '最后付款方式{0:微信,1:支付宝}'")
    private Integer payType;



    @Transient
    private List<ODetails> oDetailsList;

}
