package com.cyyaw.store.service.impl;

import com.cyyaw.jpa.BaseDao;
import com.cyyaw.jpa.BaseService;
import com.cyyaw.store.service.GdDepositoryService;
import com.cyyaw.store.table.goods.dao.GdDepositoryDao;
import com.cyyaw.store.table.goods.entity.GdDepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class GdDepositoryServiceImpl extends BaseService<GdDepository, Integer> implements GdDepositoryService {

    @Autowired
    private GdDepositoryDao gdDepositoryDao;

    @Override
    public BaseDao getBaseDao() {
        return gdDepositoryDao;
    }

}

