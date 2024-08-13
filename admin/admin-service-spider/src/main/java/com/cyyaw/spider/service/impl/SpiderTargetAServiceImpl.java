package com.cyyaw.spider.service.impl;

import com.cyyaw.jpa.BaseDao;
import com.cyyaw.jpa.BaseService;
import com.cyyaw.spider.service.SpiderTargetAService;
import com.cyyaw.spider.table.entity.SpiderTargetA;
import com.cyyaw.spider.table.dao.SpiderTargetADao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class SpiderTargetAServiceImpl extends BaseService<SpiderTargetA, Integer> implements SpiderTargetAService {

    @Autowired
    private SpiderTargetADao spiderTargetADao;

    @Override
    public BaseDao getBaseDao() {
        return spiderTargetADao;
    }

}

