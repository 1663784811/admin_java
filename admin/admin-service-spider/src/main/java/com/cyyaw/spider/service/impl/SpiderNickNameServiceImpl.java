package com.cyyaw.spider.service.impl;

import com.cyyaw.jpa.BaseDao;
import com.cyyaw.jpa.BaseService;
import com.cyyaw.spider.service.SpiderNickNameService;
import com.cyyaw.spider.table.entity.SpiderNickName;
import com.cyyaw.spider.table.dao.SpiderNickNameDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class SpiderNickNameServiceImpl extends BaseService<SpiderNickName, Integer> implements SpiderNickNameService {

    @Autowired
    private SpiderNickNameDao spiderNickNameDao;

    @Override
    public BaseDao getBaseDao() {
        return spiderNickNameDao;
    }

}

