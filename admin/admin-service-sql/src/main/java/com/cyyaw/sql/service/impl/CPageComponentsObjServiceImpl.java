package com.cyyaw.sql.service.impl;

import com.cyyaw.jpa.BaseDao;
import com.cyyaw.jpa.BaseService;
import com.cyyaw.sql.service.CPageComponentsLogService;
import com.cyyaw.sql.service.CPageComponentsObjService;
import com.cyyaw.sql.table.dao.CPageComponentsLogDao;
import com.cyyaw.sql.table.dao.CPageComponentsObjDao;
import com.cyyaw.sql.table.entity.CPageComponentsLog;
import com.cyyaw.sql.table.entity.CPageComponentsObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CPageComponentsObjServiceImpl extends BaseService<CPageComponentsObj, Integer> implements CPageComponentsObjService {

    @Autowired
    private CPageComponentsObjDao componentsObjDao;

    @Override
    public BaseDao getBaseDao() {
        return componentsObjDao;
    }
}
