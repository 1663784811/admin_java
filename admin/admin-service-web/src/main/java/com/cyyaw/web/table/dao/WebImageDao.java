package com.cyyaw.web.table.dao;

import com.cyyaw.jpa.BaseDao;
import com.cyyaw.web.table.entity.WebImage;
import org.springframework.data.jpa.repository.Query;

public interface WebImageDao extends BaseDao<WebImage, Integer> {


    @Query("select m from WebImage m where m.tid = ?1")
    WebImage findByTid(String tid);

}
