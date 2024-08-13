package com.cyyaw.signin.table.dao;

import com.cyyaw.jpa.BaseDao;
import com.cyyaw.signin.table.entity.SiSignIn;
import org.springframework.data.jpa.repository.Query;


public interface SiSignInDao extends BaseDao<SiSignIn, Integer> {


    @Query("select m from SiSignIn m where m.tid = ?1")
    SiSignIn findByTid(String tid);
}
