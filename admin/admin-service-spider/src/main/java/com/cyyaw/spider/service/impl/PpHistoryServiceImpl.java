package com.cyyaw.spider.service.impl;

import com.cyyaw.jpa.BaseDao;
import com.cyyaw.jpa.BaseService;
import com.cyyaw.spider.service.PpHistoryService;
import com.cyyaw.spider.table.dao.PpHistoryDao;
import com.cyyaw.spider.table.entity.PpHistory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class PpHistoryServiceImpl extends BaseService<PpHistory, Integer> implements PpHistoryService {

    @Autowired
    private PpHistoryDao ppHistoryDao;

    @Override
    public BaseDao getBaseDao() {
        return ppHistoryDao;
    }

}

