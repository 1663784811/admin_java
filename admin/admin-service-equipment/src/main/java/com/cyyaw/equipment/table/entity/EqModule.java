package com.cyyaw.equipment.table.entity;


import com.cyyaw.jpa.util.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "eq_module")
@org.hibernate.annotations.Table(appliesTo = "eq_module", comment = "设备模块")
public class EqModule implements BaseEntity<Integer>, Serializable {
    private static final long serialVersionUID = 1568784262756870L;

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
    @Column(name = "app_id", columnDefinition = "varchar(32) COMMENT '应用ID'")
    private String appId;
    @Basic
    @Column(name = "equipment_id", columnDefinition = "varchar(32) COMMENT '设备ID'")
    private String equipmentId;
    // =================================================================================

    @Basic
    @Column(name = "code", columnDefinition = "varchar(32) COMMENT '模块序号'")
    private String code;

    @Basic
    @Column(name = "type", columnDefinition = "varchar(32) COMMENT '模块类型'")
    private String type;

    @Basic
    @Column(name = "capacity", columnDefinition = "varchar(32) COMMENT '模块容量'")
    private String capacity;

}
