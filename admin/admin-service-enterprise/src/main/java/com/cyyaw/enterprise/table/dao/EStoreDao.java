package com.cyyaw.enterprise.table.dao;

import com.cyyaw.enterprise.table.entity.EStore;
import com.cyyaw.jpa.BaseDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EStoreDao extends BaseDao<EStore, Integer> {


    @Query("select m from EStore m where m.tid in ( :storegoodsids ) ")
    List<EStore> findByTidIn(@Param("storegoodsids") List<String> storegoodsids);

    @Query("select m from EStore m where m.tid = ?1")
    EStore findByTid(String tid);
}
