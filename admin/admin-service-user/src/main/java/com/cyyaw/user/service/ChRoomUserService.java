package com.cyyaw.user.service;

import com.cyyaw.jpa.BaseTableService;
import com.cyyaw.user.table.entity.ChRoomUser;

import java.util.List;

public interface ChRoomUserService extends BaseTableService<ChRoomUser, Integer> {


    /**
     * 查询房间里的用户
     */
    List<ChRoomUser> findRoomUserByRoomId(String roomId);



}
