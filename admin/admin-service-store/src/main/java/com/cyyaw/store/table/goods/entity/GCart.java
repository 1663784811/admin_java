package com.cyyaw.store.table.goods.entity;

import com.cyyaw.jpa.util.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "g_cart")
@org.hibernate.annotations.Table(appliesTo = "g_cart", comment = "我的购物车")
public class GCart implements BaseEntity<Integer>,  Serializable {
    private static final long serialVersionUID = 156878272233758L;

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
    @Column(name = "user_id", columnDefinition = "varchar(32) COMMENT 'u_user用户表(当前用户)id'")
    private String userId;

    @Basic
    @Column(name = "store_id", columnDefinition = "varchar(32) COMMENT '所属门店e_storeid表ID'")
    private String storeId;

    @Basic
    @Column(name = "goods_id", columnDefinition = "varchar(32) not null COMMENT '商品表ID/skuID'")
    private String goodsId;
    @Basic
    @Column(name = "sku_id", columnDefinition = "varchar(32) not null COMMENT '所属门店e_storeid表ID'")
    private String skuId;


    // =================================
    @Basic
    @Column(name = "number", columnDefinition = "int  default '0' COMMENT '总数量'")
    private Integer number;

    // =========================
    @Transient
    private GStoreGoodsSku goodsSku;

    @Transient
    private GGoods goods;

}
