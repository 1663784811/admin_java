package com.cyyaw.pay.service.impl;

import com.cyyaw.jpa.BaseDao;
import com.cyyaw.jpa.BaseService;
import com.cyyaw.pay.service.PayInfoService;
import com.cyyaw.pay.table.dao.PayInfoDao;
import com.cyyaw.pay.table.entity.PayInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class PayInfoServiceImpl extends BaseService<PayInfo, Integer> implements PayInfoService {

    @Autowired
    private PayInfoDao payInfoDao;

    @Override
    public BaseDao getBaseDao() {
        return payInfoDao;
    }

}

