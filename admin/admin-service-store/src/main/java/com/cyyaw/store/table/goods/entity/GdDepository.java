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
@Table(name = "gd_depository")
@org.hibernate.annotations.Table(appliesTo = "gd_depository", comment = "仓库表")
public class GdDepository implements BaseEntity<Integer>,  Serializable {
    private static final long serialVersionUID = 13687826273933758L;

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
    @Column(name = "enterprise_code", columnDefinition = "varchar(32) COMMENT '所属企业e_enterprise表code'")
    private String enterpriseCode;

    @Basic
    @Column(name = "store_id", columnDefinition = "varchar(32) COMMENT '所属门店e_storeid表ID'")
    private String storeId;



    // =================================================================================

    @Basic
    @Column(name = "name",  columnDefinition = "varchar(255) not null COMMENT '仓库名称'")
    private String name;
    @Basic
    @Column(name = "address",   columnDefinition = "varchar(255) COMMENT '仓库地址'")
    private String address;

    @Basic
    @Column(name = "type", columnDefinition = "int default '0' COMMENT '仓库类型{1:正品仓库,2:赠品仓库}'")
    private String type;


}
