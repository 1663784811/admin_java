package com.cyyaw.user.table.entity;

import com.cyyaw.jpa.util.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "t_admin_message")
@org.hibernate.annotations.Table(appliesTo = "t_admin_message", comment = "管理员_消息表")
public class TAdminMessage implements BaseEntity<Integer>,  Serializable {
    private static final long serialVersionUID = 1568782627113938L;

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
    @Column(name = "status",columnDefinition = "int COMMENT '状态{0:未读,1:已读}'")
    private Integer status;

    @Basic
    @Column(name = "tadminid", columnDefinition = "varchar(32) COMMENT 'admin表id'")
    private String tadminid;

    @Basic
    @Column(name = "tmessageid", columnDefinition = "varchar(32) COMMENT '消息表id'")
    private String tmessageid;
}
