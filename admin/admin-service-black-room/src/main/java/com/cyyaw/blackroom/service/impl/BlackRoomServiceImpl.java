package com.cyyaw.blackroom.service.impl;

import com.cyyaw.blackroom.service.BlackRoomService;
import com.cyyaw.blackroom.table.dao.BlackRoomDao;
import com.cyyaw.blackroom.table.entity.BlackRoom;
import com.cyyaw.jpa.BaseDao;
import com.cyyaw.jpa.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class BlackRoomServiceImpl extends BaseService<BlackRoom, Integer> implements BlackRoomService {

    @Autowired
    private BlackRoomDao blackRoomDao;

    @Override
    public BaseDao getBaseDao() {
        return blackRoomDao;
    }



}

