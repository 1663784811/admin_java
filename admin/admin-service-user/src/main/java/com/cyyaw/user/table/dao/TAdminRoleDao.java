package com.cyyaw.user.table.dao;

import com.cyyaw.jpa.BaseDao;
import com.cyyaw.user.table.entity.TAdminRole;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TAdminRoleDao extends BaseDao<TAdminRole, Integer> {


    @Query("select m from TAdminRole m where m.adminId =?1 and m.roleId =?2 ")
    List<TAdminRole> findByAdminIdAndRoleID(String adminId, String roleId);


}
