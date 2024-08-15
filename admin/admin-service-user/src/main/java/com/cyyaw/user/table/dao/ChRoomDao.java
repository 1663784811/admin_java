package com.cyyaw.user.table.dao;


import com.cyyaw.jpa.BaseDao;
import com.cyyaw.user.table.entity.ChRoom;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChRoomDao extends BaseDao<ChRoom, Integer> {


    @Query("select m from ChRoom m where m.tid =?1")
    ChRoom findByTid(String tid);


    @Query("select m from ChRoom m where m.tid in (:roomIdList)")
    List<ChRoom> findByTidIn(@Param("roomIdList") List<String> roomIdList);


    @Query("select m from ChRoom m where m.tid in ( select t.roomId from ChRoomUser t where t.userId = ?1 )")
    List<ChRoom> findByUserId(String userId);

}
