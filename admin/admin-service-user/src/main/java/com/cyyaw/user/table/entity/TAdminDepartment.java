package com.cyyaw.user.table.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "t_admin_department")
@org.hibernate.annotations.Table(appliesTo = "t_admin_department", comment = "管理员部门表")
public class TAdminDepartment implements Serializable {

    private static final long serialVersionUID = 156878627170401L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic
    @Column(name = "id", columnDefinition = "int auto_increment not null COMMENT 'id'")
    private Integer id;

    @Basic
    @Column(name = "admin_id", columnDefinition = "varchar(32) not null default '' COMMENT '管理员ID'")
    private String adminId;

    @Basic
    @Column(name = "department_id", columnDefinition = "varchar(32) not null default '' COMMENT '部门ID'")
    private String departmentId;


}
