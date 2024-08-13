package com.cyyaw.service.impl;

import com.cyyaw.jpa.BaseDao;
import com.cyyaw.jpa.BaseService;
import com.cyyaw.service.CpCompanyService;
import com.cyyaw.spider.table.company.dao.CpCompanyDao;
import com.cyyaw.spider.table.company.entity.CpCompany;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class CpCompanyServiceImpl extends BaseService<CpCompany, Integer> implements CpCompanyService {

    @Autowired
    private CpCompanyDao cpCompanyDao;

    @Override
    public BaseDao getBaseDao() {
        return cpCompanyDao;
    }

}

