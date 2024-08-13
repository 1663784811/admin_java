package com.cyyaw.spider.service.impl;

import com.cyyaw.jpa.BaseDao;
import com.cyyaw.jpa.BaseService;
import com.cyyaw.spider.service.PpPeopleService;
import com.cyyaw.spider.table.dao.PpPeopleDao;
import com.cyyaw.spider.table.entity.PpPeople;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class PpPeopleServiceImpl extends BaseService<PpPeople, Integer> implements PpPeopleService {

    @Autowired
    private PpPeopleDao ppPeopleDao;

    @Override
    public BaseDao getBaseDao() {
        return ppPeopleDao;
    }

}

