package com.cyyaw.enterprise.service.impl;

import com.cyyaw.enterprise.service.EStoreService;
import com.cyyaw.enterprise.table.dao.EStoreDao;
import com.cyyaw.enterprise.table.entity.EStore;
import com.cyyaw.jpa.BaseDao;

import com.cyyaw.jpa.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class EStoreServiceImpl extends BaseService<EStore, Integer> implements EStoreService {

    @Autowired
    private EStoreDao eStoreDao;

    @Override
    public BaseDao getBaseDao() {
        return eStoreDao;
    }

}

