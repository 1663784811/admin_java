package com.cyyaw.user.service.impl;

import com.cyyaw.jpa.BaseDao;
import com.cyyaw.jpa.BaseService;
import com.cyyaw.user.service.ChRoomService;
import com.cyyaw.user.table.dao.ChRoomDao;
import com.cyyaw.user.table.dao.ChRoomUserDao;
import com.cyyaw.user.table.dao.UUserDao;
import com.cyyaw.user.table.entity.ChRoom;
import com.cyyaw.user.table.entity.ChRoomUser;
import com.cyyaw.user.table.entity.UUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
public class ChRoomServiceImpl extends BaseService<ChRoom, Integer> implements ChRoomService {

    @Autowired
    private ChRoomDao chRoomDao;

    @Autowired
    private ChRoomUserDao chRoomUserDao;

    @Autowired
    private UUserDao userDao;

    @Override
    public BaseDao getBaseDao() {
        return chRoomDao;
    }

    @Override
    public ChRoom findByTid(String tid) {
        return chRoomDao.findByTid(tid);
    }

    @Override
    public List<ChRoom> findMyRoom(String userId) {
        List<String> roomIdList = new ArrayList<>();
        // 查房间
        List<ChRoom> rooms = chRoomDao.findByUserId(userId);
        for (int i = 0; i < rooms.size(); i++) {
            ChRoom chRoom = rooms.get(i);
            Integer type = chRoom.getType();
            if (type == 0) {
                // 查询私聊的
                roomIdList.add(chRoom.getTid());
            }
        }
        // 查询私聊用户


        return rooms;
    }
}

