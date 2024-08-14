package com.cyyaw.user.service.impl;

import com.cyyaw.jpa.BaseDao;
import com.cyyaw.jpa.BaseService;
import com.cyyaw.user.service.ChMessageService;
import com.cyyaw.user.table.dao.ChMessageDao;
import com.cyyaw.user.table.entity.ChMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Slf4j
@Service
public class ChMessageServiceImpl extends BaseService<ChMessage, Integer> implements ChMessageService {

    @Autowired
    private ChMessageDao chMessageDao;

    @Override
    public BaseDao getBaseDao() {
        return chMessageDao;
    }

}

