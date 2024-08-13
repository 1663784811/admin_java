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
@Table(name = "gd_log")
@org.hibernate.annotations.Table(appliesTo = "gd_log", comment = "进出库记录表")
public class GdLog implements BaseEntity<Integer>,  Serializable {
    private static final long serialVersionUID = 13787826273933758L;

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
    @Column(name = "depository_id", columnDefinition = "varchar(32) COMMENT '所属仓库g_depository表ID'")
    private String depositoryId;
    @Basic
    @Column(name = "goods_id", columnDefinition = "varchar(32) COMMENT '货品ID'")
    private String goodsId;

    // ================================================================================
    @Basic
    @Column(name = "code_no", columnDefinition = "varchar(32) COMMENT '批次号'")
    private String codeNo;

    @Basic
    @Column(name = "name", columnDefinition = "varchar(255) COMMENT '货品名'")
    private String name;


    @Basic
    @Column(name = "number",  columnDefinition = "int not null default '0' COMMENT '数量'")
    private Integer number;

    @Basic
    @Column(name = "type",  columnDefinition = "int not null COMMENT '类型{1:入库,2:出库}'")
    private Integer type;

    @Basic
    @Column(name = "details", columnDefinition = "varchar(255) COMMENT '描述'")
    private String details;

}
