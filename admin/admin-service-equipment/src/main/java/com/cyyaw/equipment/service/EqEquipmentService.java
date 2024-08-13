package com.cyyaw.equipment.service;


import com.cyyaw.equipment.table.entity.EqEquipment;
import com.cyyaw.jpa.BaseTableService;

public interface EqEquipmentService extends BaseTableService<EqEquipment, Integer> {

    EqEquipment findByCode(String code);


    EqEquipment findByTid(String tid);




}
