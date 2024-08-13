package com.cyyaw.store.service.impl;

import com.cyyaw.jpa.BaseDao;
import com.cyyaw.jpa.BaseService;
import com.cyyaw.store.service.GDetailsService;
import com.cyyaw.store.table.goods.dao.GDetailsDao;
import com.cyyaw.store.table.goods.entity.GDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class GDetailsServiceImpl extends BaseService<GDetails, Integer> implements GDetailsService {

    @Autowired
    private GDetailsDao gDetailsDao;

    @Override
    public BaseDao getBaseDao() {
        return gDetailsDao;
    }

}

