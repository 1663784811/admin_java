package com.cyyaw.signin.service.impl;

import com.cyyaw.jpa.BaseDao;
import com.cyyaw.jpa.BaseService;
import com.cyyaw.signin.service.SiSignInService;
import com.cyyaw.signin.table.dao.SiSignInDao;
import com.cyyaw.signin.table.entity.SiSignIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SiSignInServiceImpl extends BaseService<SiSignIn, Integer> implements SiSignInService{

    @Autowired
    private SiSignInDao siSignInDao;

    @Override
    public BaseDao getBaseDao() {
        return siSignInDao;
    }


    @Override
    public SiSignIn findByTid(String tid) {
        return siSignInDao.findByTid(tid);
    }
}
