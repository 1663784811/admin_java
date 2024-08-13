package com.cyyaw.service.impl;

import com.cyyaw.jpa.BaseDao;
import com.cyyaw.jpa.BaseService;
import com.cyyaw.service.StWordService;
import com.cyyaw.spider.table.dao.StWordDao;
import com.cyyaw.spider.table.entity.StWord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class StWordServiceImpl extends BaseService<StWord, Integer> implements StWordService {

    @Autowired
    private StWordDao stWordDao;

    @Override
    public BaseDao getBaseDao() {
        return stWordDao;
    }

}

