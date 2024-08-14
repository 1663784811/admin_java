package com.cyyaw.user.service.impl;

import com.cyyaw.jpa.BaseDao;
import com.cyyaw.jpa.BaseService;
import com.cyyaw.user.service.ChRoomService;
import com.cyyaw.user.table.dao.ChRoomDao;
import com.cyyaw.user.table.entity.ChRoom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
public class ChRoomServiceImpl extends BaseService<ChRoom, Integer> implements ChRoomService {

    @Autowired
    private ChRoomDao chRoomDao;

    @Override
    public BaseDao getBaseDao() {
        return chRoomDao;
    }

    @Override
    public ChRoom findByTid(String tid) {
        return chRoomDao.findByTid(tid);
    }
}

