package com.cyyaw.user.table.dao;


import com.cyyaw.jpa.BaseDao;
import com.cyyaw.user.table.entity.ChMessage;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChMessageDao extends BaseDao<ChMessage, Integer> {


    @Query("select m from ChMessage m where m.status=0 and m.roomId in ( select t.roomId from  ChRoomUser t where t.userId = ?1 )")
    List<ChMessage> findUnReadMsgByUserId(String userId);


}
