package com.cyyaw.enterprise.service.impl;

import com.cyyaw.enterprise.service.EProductCenterService;
import com.cyyaw.enterprise.table.dao.EProductCenterDao;
import com.cyyaw.enterprise.table.entity.EProductCenter;
import com.cyyaw.jpa.BaseDao;
import com.cyyaw.jpa.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Slf4j
@Service
public class EProductCenterServiceImpl extends BaseService<EProductCenter, Integer> implements EProductCenterService {

    @Autowired
    private EProductCenterDao eProductCenterDao;

    @Override
    public BaseDao getBaseDao() {
        return eProductCenterDao;
    }

}

