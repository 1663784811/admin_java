package com.cyyaw.equipment.service.impl;

import com.cyyaw.equipment.service.EqEquipmentService;
import com.cyyaw.equipment.table.dao.EqEquipmentDao;
import com.cyyaw.equipment.table.entity.EqEquipment;
import com.cyyaw.jpa.BaseDao;

import com.cyyaw.jpa.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
public class EqEquipmentServiceImpl extends BaseService<EqEquipment, Integer> implements EqEquipmentService {

    @Autowired
    private EqEquipmentDao eqEquipmentDao;

    @Override
    public BaseDao getBaseDao() {
        return eqEquipmentDao;
    }


    @Override
    public EqEquipment findByCode(String code) {
        List<EqEquipment> equipmentList = eqEquipmentDao.findByCode(code);
        int size = equipmentList.size();
        if (size == 1) {
            return equipmentList.get(0);
        } else if (size > 1) {
            log.info("存在多个设备code");
        }
        return null;
    }

    @Override
    public EqEquipment findByTid(String tid) {
        return eqEquipmentDao.findByTid(tid);
    }

}

