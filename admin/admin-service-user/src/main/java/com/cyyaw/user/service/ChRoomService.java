package com.cyyaw.user.service;

import com.cyyaw.jpa.BaseTableService;
import com.cyyaw.user.table.entity.ChRoom;

public interface ChRoomService extends BaseTableService<ChRoom, Integer> {


    ChRoom findByTid(String tid);



}
