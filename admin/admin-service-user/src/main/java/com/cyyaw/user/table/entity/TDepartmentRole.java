package com.cyyaw.user.table.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "t_role_department")
@org.hibernate.annotations.Table(appliesTo = "t_role_department", comment = "部门角色表")
public class TDepartmentRole implements Serializable {

    private static final long serialVersionUID = 15687627170401L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic
    @Column(name = "id", columnDefinition = "int auto_increment not null COMMENT 'id'")
    private Integer id;

    @Basic
    @Column(name = "role_id", columnDefinition = "varchar(32) not null default '' COMMENT '角色ID'")
    private String roleId;

    @Basic
    @Column(name = "department_id", columnDefinition = "varchar(32) not null default '' COMMENT '部门ID'")
    private String departmentId;

}
