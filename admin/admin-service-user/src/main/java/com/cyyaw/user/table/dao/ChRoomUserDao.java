package com.cyyaw.user.table.dao;


import com.cyyaw.jpa.BaseDao;
import com.cyyaw.user.table.entity.ChRoomUser;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChRoomUserDao extends BaseDao<ChRoomUser, Integer> {


    @Query("select m from ChRoomUser m where m.roomId = ?1")
    List<ChRoomUser> findByRoomId(String roomId);


}
