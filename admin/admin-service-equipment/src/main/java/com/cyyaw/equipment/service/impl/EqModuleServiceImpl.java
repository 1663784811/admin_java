package com.cyyaw.equipment.service.impl;

import com.cyyaw.equipment.service.EqEquipmentService;
import com.cyyaw.equipment.service.EqModuleService;
import com.cyyaw.equipment.table.dao.EqEquipmentDao;
import com.cyyaw.equipment.table.dao.EqModuleDao;
import com.cyyaw.equipment.table.entity.EqEquipment;
import com.cyyaw.equipment.table.entity.EqModule;
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
public class EqModuleServiceImpl extends BaseService<EqModule, Integer> implements EqModuleService {

    @Autowired
    private EqModuleDao eqModuleDao;

    @Override
    public BaseDao getBaseDao() {
        return eqModuleDao;
    }


}

