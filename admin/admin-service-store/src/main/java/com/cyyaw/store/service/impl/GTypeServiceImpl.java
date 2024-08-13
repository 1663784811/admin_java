package com.cyyaw.store.service.impl;

import com.cyyaw.jpa.BaseDao;
import com.cyyaw.jpa.BaseService;
import com.cyyaw.store.service.GTypeService;
import com.cyyaw.store.table.goods.dao.GTypeDao;
import com.cyyaw.store.table.goods.entity.GType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
public class GTypeServiceImpl extends BaseService<GType, Integer> implements GTypeService {

    @Autowired
    private GTypeDao gTypeDao;

    @Override
    public BaseDao getBaseDao() {
        return gTypeDao;
    }

}

