package com.cyyaw.user.service;

import com.cyyaw.jpa.BaseTableService;
import com.cyyaw.user.table.entity.ChRoom;

import java.util.List;

public interface ChRoomService extends BaseTableService<ChRoom, Integer> {


    ChRoom findByTid(String tid);


    /**
     * 查询我的房间
     */
    List<ChRoom> findMyRoom(String userId);

}
