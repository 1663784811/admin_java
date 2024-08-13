package com.cyyaw.store.service.impl;

import com.cyyaw.jpa.BaseDao;
import com.cyyaw.jpa.BaseService;
import com.cyyaw.store.service.OOrderService;
import com.cyyaw.store.table.order.dao.ODetailsDao;
import com.cyyaw.store.table.order.dao.OOrderDao;
import com.cyyaw.store.table.order.entity.OOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class OOrderServiceImpl extends BaseService<OOrder, Integer> implements OOrderService {

    @Autowired
    private OOrderDao oOrderDao;

    @Autowired
    private ODetailsDao oDetailsDao;

    @Override
    public BaseDao getBaseDao() {
        return oOrderDao;
    }

}

