package com.cyyaw.store.service.impl;

import com.cyyaw.jpa.BaseDao;
import com.cyyaw.jpa.BaseService;
import com.cyyaw.store.service.GGoodsSearchService;
import com.cyyaw.store.table.goods.dao.GGoodsSearchDao;
import com.cyyaw.store.table.goods.entity.GGoodsSearch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Slf4j
@Service
public class GGoodsSearchServiceImpl extends BaseService<GGoodsSearch, Integer> implements GGoodsSearchService {

    @Autowired
    private GGoodsSearchDao gGoodsSearchDao;

    @Override
    public BaseDao getBaseDao() {
        return gGoodsSearchDao;
    }

}

