package com.cyyaw.user.table.dao;

import com.cyyaw.jpa.BaseDao;
import com.cyyaw.user.table.entity.TPower;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TPowerDao extends BaseDao<TPower, Integer> {

    @Query("select m from TPower m where m.tid in ( select t from TAdminPower t where t.adminId = ?1)")
    List<TPower> findAdminPower(String tid);


    @Query("select m from TPower m where m.tid in ( select t.powerId from TRolePower t where t.roleId in (:rolesId)) and m.userType = :userType")
    List<TPower> findPowerByRoleAndUserType(@Param("rolesId") List<String> rolesId, @Param("userType") Integer userType);


    @Query("select m from TPower m where m.tid in( select t.powerId from TAdminPower t where t.adminId = ?1) or m.isPower = 0 order by m.sort asc ")
    List<TPower> getAdminMenu(String tid);


    @Query("select m from TPower m where m.tid in ( select t.powerId from TAdminPower t where t.adminId = ?1)")
    List<TPower> getTPowerByTAdminTid(String tid);


    @Query("select m from TPower m where m.tid = ?1")
    TPower findByTid(String pid);


    @Query("select m from TPower m where m.enterpriseCode = ?1 and m.powerType = ?2 ")
    List<TPower> findAllByEnterpriseCodeAndType(String eCode, Integer powerType);


}
