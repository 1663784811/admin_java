package com.cyyaw.store.table.goods.dao;


import com.cyyaw.jpa.BaseDao;
import com.cyyaw.store.table.goods.entity.GDetails;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GDetailsDao extends BaseDao<GDetails, Integer> {

    @Query("select m from GDetails m where m.goodsId = ?1")
    List<GDetails> findByGoodsId(String goodsId);

}
