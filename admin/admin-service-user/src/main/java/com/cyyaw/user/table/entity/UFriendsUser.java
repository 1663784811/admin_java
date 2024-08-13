package com.cyyaw.user.table.entity;

import com.cyyaw.jpa.util.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Data
@Entity
@Table(name = "u_friends_user")
@org.hibernate.annotations.Table(appliesTo = "u_friends_user", comment = "好友关联表")
public class UFriendsUser implements BaseEntity<Integer>, Serializable {

    private static final long serialVersionUID = 1366301723582985L;

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
    @Column(name = "user_id", columnDefinition = "varchar(32) not null COMMENT 'u_user用户表(当前用户)id'")
    private String userId;

    @Basic
    @Column(name = "to_user_id", columnDefinition = "varchar(32) not null COMMENT 'u_user用户表(好友)id'")
    private String toUserId;


    @Basic
    @Column(name = "friends_group_id", columnDefinition = "varchar(32) COMMENT 'u_friends_group好友分组表id'")
    private String friendsGroupId;


    @Transient
    private UUser toUser;


}
