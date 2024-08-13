package com.cyyaw.sql.service.impl;

import com.cyyaw.jpa.BaseDao;
import com.cyyaw.jpa.BaseService;
import com.cyyaw.sql.service.CPageComponentsLogService;
import com.cyyaw.sql.table.dao.CPageComponentsLogDao;
import com.cyyaw.sql.table.entity.CPageComponentsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CPageComponentsLogServiceImpl extends BaseService<CPageComponentsLog, Integer> implements CPageComponentsLogService {

    @Autowired
    private CPageComponentsLogDao cPageComponentsLogDao;

    @Override
    public BaseDao getBaseDao() {
        return cPageComponentsLogDao;
    }
}
