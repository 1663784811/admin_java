package com.cyyaw.user.service.impl;

import com.cyyaw.jpa.BaseDao;
import com.cyyaw.jpa.BaseService;
import com.cyyaw.user.service.ChRoomUserService;
import com.cyyaw.user.table.dao.ChRoomUserDao;
import com.cyyaw.user.table.entity.ChRoomUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
public class ChRoomUserServiceImpl extends BaseService<ChRoomUser, Integer> implements ChRoomUserService {

    @Autowired
    private ChRoomUserDao chRoomUserDao;

    @Override
    public BaseDao getBaseDao() {
        return chRoomUserDao;
    }

    @Override
    public List<ChRoomUser> findRoomUserByRoomId(String roomId) {
        return chRoomUserDao.findByRoomId(roomId);
    }
}

