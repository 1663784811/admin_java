package com.cyyaw.store.table.goods.dao;

import com.cyyaw.jpa.BaseDao;
import com.cyyaw.store.table.goods.entity.GCart;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GCartDao extends BaseDao<GCart, Integer> {


    @Query("select m from GCart m where m.userId = ?1 ")
    List<GCart> myCar(String uid, PageRequest of);

    @Query("select m from GCart m where m.userId = ?2 and m.skuId =?1")
    List<GCart> findBySkuIdAndUid(String skuId, String uid);

    @Query("select sum(m.number) from GCart m where m.userId =?1")
    Integer countByUid(String uid);

    @Query("select m from GCart m where m.userId = ?1 and m.storeId in ( ?2 )")
    List<GCart> findByUidAndStoreIdIn(String uid, List<String> storeIdList);


    @Query("select m from GCart m where m.tid = ?1 and m.userId = ?2")
    List<GCart> findByTidAndUserId(String cartTid, String userId);


}
