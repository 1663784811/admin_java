package com.cyyaw.equipment.table.dao;


import com.cyyaw.equipment.table.entity.EqEquipment;
import com.cyyaw.jpa.BaseDao;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EqEquipmentDao extends BaseDao<EqEquipment, Integer> {


    @Query("select m from EqEquipment m where m.code = ?1")
    List<EqEquipment> findByCode(String code);


    @Query("select m from EqEquipment m where m.tid = ?1")
    EqEquipment findByTid(String tid);
}
