package com.cyyaw.store.service.impl;

import com.cyyaw.jpa.BaseDao;
import com.cyyaw.jpa.BaseService;
import com.cyyaw.store.service.GdGoodsService;
import com.cyyaw.store.table.goods.dao.GDepositoryGoodsDao;
import com.cyyaw.store.table.goods.entity.GdGoods;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class GdGoodsServiceImpl extends BaseService<GdGoods, Integer> implements GdGoodsService {

    @Autowired
    private GDepositoryGoodsDao gDepositoryGoodsDao;

    @Override
    public BaseDao getBaseDao() {
        return gDepositoryGoodsDao;
    }

}

