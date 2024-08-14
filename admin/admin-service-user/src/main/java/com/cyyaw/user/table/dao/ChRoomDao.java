package com.cyyaw.user.table.dao;


import com.cyyaw.jpa.BaseDao;
import com.cyyaw.user.table.entity.ChRoom;
import org.springframework.data.jpa.repository.Query;

public interface ChRoomDao extends BaseDao<ChRoom, Integer> {


    @Query("select m from ChRoom m where m.tid =?1")
    ChRoom findByTid(String tid);

}
