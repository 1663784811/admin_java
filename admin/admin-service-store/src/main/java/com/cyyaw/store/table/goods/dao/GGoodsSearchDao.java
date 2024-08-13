package com.cyyaw.store.table.goods.dao;


import com.cyyaw.jpa.BaseDao;
import com.cyyaw.store.table.goods.entity.GGoodsSearch;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GGoodsSearchDao extends BaseDao<GGoodsSearch, Integer> {


    @Query("select m from GGoodsSearch m where m.del = 0 and m.name like concat('%',?1,'%')")
    List<GGoodsSearch> searchGoods(String searchName);

    @Query("select m from GGoodsSearch m where m.goodsId = ?1")
    GGoodsSearch findByGoodsId(String goodsId);


}
