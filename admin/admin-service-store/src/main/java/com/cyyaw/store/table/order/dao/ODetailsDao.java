package com.cyyaw.store.table.order.dao;


import com.cyyaw.jpa.BaseDao;
import com.cyyaw.store.table.order.entity.ODetails;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ODetailsDao extends BaseDao<ODetails, Integer> {


    @Query("select m from ODetails  m where m.orderId in (:orderIdArr)")
    List<ODetails> findByOrderIdArr(List<String> orderIdArr);

    @Query("select m from ODetails  m where m.orderId = ?1")
    List<ODetails> findByOrderId(String orderId);


}
