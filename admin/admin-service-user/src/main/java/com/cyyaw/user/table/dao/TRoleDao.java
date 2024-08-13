package com.cyyaw.user.table.dao;


import com.cyyaw.jpa.BaseDao;
import com.cyyaw.user.table.entity.TRole;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TRoleDao extends BaseDao<TRole, Integer> {


    @Query("select m from TRole m where m.tid in ( select t.roleId from TAdminRole t where t.adminId = ?1)")
    List<TRole> findByAdminId(String tid);

    @Query("select m from TRole m where m.tid in ( select s.roleId from TAdminRole s where s.adminId = ?1 )")
    List<TRole> getRolesByTAdminTid(String tid);

}
