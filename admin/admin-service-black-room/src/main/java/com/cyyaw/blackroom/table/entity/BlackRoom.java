package com.cyyaw.blackroom.table.entity;


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
@Table(name = "black_room")
@org.hibernate.annotations.Table(appliesTo = "black_room", comment = "小黑屋名单")
public class BlackRoom implements BaseEntity<Integer>, Serializable {
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

    // =================================================================================  索引
    @Basic
    @Column(name = "app_id", columnDefinition = "varchar(32) COMMENT '应用ID'")
    private String appId;
    @Basic
    @Column(name = "admin_id", columnDefinition = "varchar(32) COMMENT '记录人ID'")
    private String adminId;

    // =================================================================================
    @Basic
    @Column(name = "name", columnDefinition = "varchar(255) COMMENT '名称'")
    private String name;
    @Basic
    @Column(name = "account", columnDefinition = "varchar(255) COMMENT '账号'")
    private String account;
    @Basic
    @Column(name = "type", columnDefinition = "int default '0' COMMENT '类型{0:其它,1:微信,2:支付宝}'")
    private Integer type;
    @Basic
    @Column(name = "other_type", columnDefinition = "varchar(255) COMMENT '其它类型名称'")
    private String otherType;
    @Basic
    @Column(name = "price", columnDefinition = "decimal(18,2)  default '-1' COMMENT '钱'")
    private BigDecimal price;
    @Basic
    @Column(name = "user_note", columnDefinition = "text COMMENT '用户描述'")
    private String userNote;

}






















