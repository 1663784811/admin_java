package com.cyyaw.sql.table.dao;


import com.cyyaw.jpa.BaseDao;
import com.cyyaw.sql.table.entity.CPageComponents;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CPageComponentsDao extends BaseDao<CPageComponents, Integer> {


    @Query("select m from CPageComponents m where pageId=?1")
    List<CPageComponents> findByPageId(String pageId);

}
