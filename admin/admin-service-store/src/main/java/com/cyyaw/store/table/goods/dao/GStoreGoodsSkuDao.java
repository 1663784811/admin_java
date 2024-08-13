package com.cyyaw.store.table.goods.dao;


import com.cyyaw.jpa.BaseDao;
import com.cyyaw.store.table.goods.entity.GStoreGoodsSku;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GStoreGoodsSkuDao extends BaseDao<GStoreGoodsSku, Integer> {


    @Query("select m from GStoreGoodsSku m where m.goodsId in (:goodsids)")
    List<GStoreGoodsSku> findAllByGoodsId(List<String> goodsids);

    @Query("select m from GStoreGoodsSku m where m.goodsId = ?1")
    List<GStoreGoodsSku> findAllByGoodsId(String goodsId);

    @Query("select m from GStoreGoodsSku m where m.tid =?1")
    GStoreGoodsSku findByTid(String skuid);

    @Query("select m from GStoreGoodsSku m where m.tid in (:tidArr)")
    List<GStoreGoodsSku> findByTidIn(List<String> tidArr);

}
