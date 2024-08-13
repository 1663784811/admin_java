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
@Table(name = "o_details")
@org.hibernate.annotations.Table(appliesTo = "o_details", comment = "订单详情表")
public class ODetails implements BaseEntity<Integer>,  Serializable {
    private static final long serialVersionUID = 156873326273933758L;

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
    @Column(name = "goods_id", columnDefinition = "varchar(32) not null COMMENT 'g_goods商品表ID'")
    private String goodsId;
    @Basic
    @Column(name = "sku_id", columnDefinition = "varchar(32) COMMENT 'skuid表ID'")
    private String skuId;
    // =================================================================================

    @Basic
    @Column(name = "type", columnDefinition = "int not null default '0' COMMENT '商品类型{0:普通商品,1:赠品}'")
    private Integer type;


    @Basic
    @Column(name = "name",  columnDefinition = "varchar(255) not null COMMENT '商品名称'")
    private String name;
    @Basic
    @Column(name = "photo",  columnDefinition = "text COMMENT '商品主图片'")
    private String photo;

    @Basic
    @Column(name="price", columnDefinition = "decimal(18,2) COMMENT '售单价格'")
    private BigDecimal price;

    @Basic
    @Column(name="last_price", columnDefinition = "decimal(18,2) COMMENT '最后出售单价格'")
    private BigDecimal lastPrice;

    @Basic
    @Column(name = "number", columnDefinition = "int COMMENT '商品数量'")
    private Integer number;







}
