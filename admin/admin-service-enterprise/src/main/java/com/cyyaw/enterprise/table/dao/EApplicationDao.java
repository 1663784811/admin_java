package com.cyyaw.enterprise.table.dao;


import com.cyyaw.enterprise.table.entity.EApplication;
import com.cyyaw.jpa.BaseDao;
import org.springframework.data.jpa.repository.Query;

public interface EApplicationDao extends BaseDao<EApplication, Integer> {


    @Query("select m from EApplication m where m.code = ?1")
    EApplication findByCode(String code);


}
