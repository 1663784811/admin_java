package com.cyyaw.service.impl;

import com.cyyaw.jpa.BaseDao;
import com.cyyaw.jpa.BaseService;
import com.cyyaw.service.CpRecruitService;
import com.cyyaw.spider.table.company.dao.CpRecruitDao;
import com.cyyaw.spider.table.company.entity.CpRecruit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class CpRecruitServiceImpl extends BaseService<CpRecruit, Integer> implements CpRecruitService {

    @Autowired
    private CpRecruitDao cpRecruitDao;

    @Override
    public BaseDao getBaseDao() {
        return cpRecruitDao;
    }

}

