package com.cyyaw.store.table.goods.entity;


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
@Table(name = "g_store_goods_sku")
@org.hibernate.annotations.Table(appliesTo = "g_store_goods_sku", comment = "门店商品sku信息")
public class GStoreGoodsSku implements BaseEntity<Integer>,  Serializable {

    private static final long serialVersionUID = 156878266734233758L;

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
    @Column(name = "goods_id", columnDefinition = "varchar(32) not null COMMENT '商品g_goods表ID'")
    private String goodsId;

    @Basic
    @Column(name = "store_id", columnDefinition = "varchar(32) not null default '' COMMENT '门店e_store表ID'")
    private String storeId;

    // =================================================================================

    @Basic
    @Column(name = "price", columnDefinition = "decimal(18,2 ) COMMENT '价格'")
    private BigDecimal price;
    @Basic
    @Column(name = "number", columnDefinition = "int not null default '0' COMMENT '虚拟库存数量'")
    private Integer number;

    @Basic
    @Column(name = "attr", columnDefinition = "varchar(255) COMMENT 'json属性'")
    private String attr;


    @Basic
    @Column(name = "photo", columnDefinition = "text COMMENT '图片'")
    private String photo;

    // ================================
    @Transient
    private GGoods goods;

}
