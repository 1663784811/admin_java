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
@Table(name = "u_group_user")
@org.hibernate.annotations.Table(appliesTo = "u_group_user", comment = "群用户关联表")
public class UGroupUser implements BaseEntity<Integer>,  Serializable {

    private static final long serialVersionUID = 13663052723582985L;

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
    @Column(name = "user_id", columnDefinition = "varchar(32) not null COMMENT 'u_user用户表id'")
    private String userId;

    @Basic
    @Column(name = "group_id", columnDefinition = "varchar(32) not null COMMENT 'u_groupid表id'")
    private String groupId;

    @Basic
    @Column(name = "grade",columnDefinition = "int not null default '0' COMMENT '等级'")
    private Integer grade;

    @Basic
    @Column(name = "type",columnDefinition = "int not null default '0' COMMENT '用户类型{0:普通人,1:群主,2:管理员}'")
    private Integer type;


}
