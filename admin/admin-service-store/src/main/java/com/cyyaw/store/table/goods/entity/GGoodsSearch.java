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
@Table(name = "g_goods_search")
@org.hibernate.annotations.Table(appliesTo = "g_goods_search", comment = "商品搜索表")
public class GGoodsSearch implements BaseEntity<Integer>,  Serializable {

    private static final long serialVersionUID = 156878262734233758L;

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
    @Column(name = "goods_id", unique = true, columnDefinition = "varchar(32) unique not null COMMENT '商品ID'")
    private String goodsId;

    @Basic
    @Column(name = "store_goods_id", columnDefinition = "varchar(32) not null COMMENT '所属商品门店e_storeid表ID'")
    private String storeGoodsId;
    // =================================================================================

    @Basic
    @Column(name = "name", columnDefinition = "varchar(255) not null COMMENT '商品名称'")
    private String name;
    @Basic
    @Column(name = "type_code", columnDefinition = "varchar(32) COMMENT '品类Code'")
    private String typeCode;
    @Basic
    @Column(name = "brand_code", columnDefinition = "varchar(32) COMMENT '品牌Code'")
    private String brandCode;

    @Basic
    @Column(name = "low_price", columnDefinition = "decimal(18,2 ) COMMENT '最低价格'")
    private BigDecimal lowPrice;
    @Basic
    @Column(name = "high_price", columnDefinition = "decimal(18,2 ) COMMENT '最高价格'")
    private BigDecimal highPrice;


    @Basic
    @Column(name = "is_top", columnDefinition = "int not null default '0' COMMENT '是否置顶{0:否,1:是}'")
    private Integer isTop;
    @Basic
    @Column(name = "evaluate", columnDefinition = "int not null default '0' COMMENT '评价{0:0星,1:1星,2:2星,3:3星,4:4星,5:5星}'")
    private Integer evaluate;

}
