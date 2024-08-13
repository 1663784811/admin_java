package com.cyyaw.store.service.impl;

import com.cyyaw.jpa.BaseDao;
import com.cyyaw.jpa.BaseService;
import com.cyyaw.store.service.GBrandService;
import com.cyyaw.store.table.goods.dao.GBrandDao;
import com.cyyaw.store.table.goods.entity.GBrand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Slf4j
@Service
public class GBrandServiceImpl extends BaseService<GBrand, Integer> implements GBrandService {

    @Autowired
    private GBrandDao gBrandDao;

    @Override
    public BaseDao getBaseDao() {
        return gBrandDao;
    }

}

