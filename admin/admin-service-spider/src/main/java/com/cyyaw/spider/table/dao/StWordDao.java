package com.cyyaw.spider.table.dao;


import com.cyyaw.jpa.BaseDao;
import com.cyyaw.spider.table.entity.StWord;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StWordDao extends BaseDao<StWord, Integer> {


    @Query("select m from StWord m where m.word = ?1")
    List<StWord> findByWord(String word);
}
