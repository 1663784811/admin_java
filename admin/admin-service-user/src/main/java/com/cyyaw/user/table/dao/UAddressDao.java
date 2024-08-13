package com.cyyaw.user.table.dao;

import com.cyyaw.jpa.BaseDao;
import com.cyyaw.user.table.entity.UAddress;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UAddressDao extends BaseDao<UAddress, Integer> {


    @Query("select m from  UAddress  m where  m.userId = ?1")
    List<UAddress> findAll(String uid);
}
