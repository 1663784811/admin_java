package com.cyyaw.enterprise.table.dao;


import com.cyyaw.enterprise.table.entity.EEnterprise;
import com.cyyaw.jpa.BaseDao;
import org.springframework.data.jpa.repository.Query;

public interface EEnterpriseDao extends BaseDao<EEnterprise, Integer> {

    @Query("select m from  EEnterprise m where m.tid = ?1")
    EEnterprise findByEnterpriseTid(String tid);

    @Query("select m from  EEnterprise m where m.code = ?1")
    EEnterprise findByEnterpriseByCode(String code);
}
