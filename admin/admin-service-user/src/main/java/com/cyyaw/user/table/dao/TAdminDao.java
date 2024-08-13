package com.cyyaw.user.table.dao;


import com.cyyaw.jpa.BaseDao;
import com.cyyaw.user.table.entity.TAdmin;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TAdminDao extends BaseDao<TAdmin, Integer> {

    @Query("select m from TAdmin m where m.enterpriseCode = ?1 and m.account = ?2")
    TAdmin findByAccount(String eCode, String account);

    @Query("select m from TAdmin m where (m.account=?1 or m.phone=?1 or m.email=?1) and m.enterpriseCode = ?2")
    List<TAdmin> getLoginInfo(String account, String enterpriseCode);


    @Query("select m from TAdmin m left join TAppAdmin app on m.tid = app.adminId and app.appId = ?2 where m.account = ?1")
    TAdmin findByAccountAndAppId(String userName, String appId);
}
