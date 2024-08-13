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
@Table(name = "g_goods")
@org.hibernate.annotations.Table(appliesTo = "g_goods", comment = "商品表")
public class GGoods implements BaseEntity<Integer>,  Serializable {
    private static final long serialVersionUID = 15687826273933758L;

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
    @Column(name = "store_id", columnDefinition = "varchar(32) not null default '' COMMENT '门店e_store表ID'")
    private String storeId;


    @Basic
    @Column(name = "name",  columnDefinition = "varchar(255) not null COMMENT '商品名称'")
    private String name;
    @Basic
    @Column(name="price", columnDefinition = "decimal(18,2) COMMENT '价格'")
    private BigDecimal price;
    @Basic
    @Column(name="low_price", columnDefinition = "decimal(18,2) COMMENT '最低价格'")
    private BigDecimal lowPrice;
    @Basic
    @Column(name="high_price", columnDefinition = "decimal(18,2) COMMENT '最高价格'")
    private BigDecimal highPrice;
    @Basic
    @Column(name = "type_code",   columnDefinition = "varchar(32) COMMENT '品类Code'")
    private String typeCode;
    @Basic
    @Column(name = "brand_code",   columnDefinition = "varchar(32) COMMENT '品牌Code'")
    private String brandCode;
    @Basic
    @Column(name = "photo",  columnDefinition = "varchar(255) COMMENT '商品图片'")
    private String photo;
}
