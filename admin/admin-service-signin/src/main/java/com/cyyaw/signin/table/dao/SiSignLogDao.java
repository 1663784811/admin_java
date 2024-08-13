package com.cyyaw.signin.table.dao;

import com.cyyaw.jpa.BaseDao;
import com.cyyaw.signin.table.entity.SiSignLog;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface SiSignLogDao extends BaseDao<SiSignLog, Integer> {

    @Query("select m from SiSignLog m where m.signInId=?1 and m.name = ?2")
    List<SiSignLog> findAllBySignInIdAndName(String signInId, String name);
}
