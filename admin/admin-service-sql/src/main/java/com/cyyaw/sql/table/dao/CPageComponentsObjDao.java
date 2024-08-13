package com.cyyaw.sql.table.dao;


import com.cyyaw.jpa.BaseDao;
import com.cyyaw.sql.table.entity.CPageComponentsObj;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CPageComponentsObjDao extends BaseDao<CPageComponentsObj, Integer> {


    @Query("select m from CPageComponentsObj m where m.pageComponentsId in(:componentsIds)")
    List<CPageComponentsObj> findByPageComponentsIdIn(@Param("componentsIds") List<String> componentsIds);


    @Query("select m from CPageComponentsObj m where m.pageComponentsId = ?1")
    List<CPageComponentsObj> findByComponentsId(String pageComponentsId);

}
