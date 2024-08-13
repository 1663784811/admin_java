package com.cyyaw.user.table.dao;

import com.cyyaw.jpa.BaseDao;
import com.cyyaw.user.table.entity.UGroupMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface UGroupMessageDao extends BaseDao<UGroupMessage, Integer> {


    @Query("select m from UGroupMessage m where m.groupId = ?1 and m.createTime <= ?2 ")
    List<UGroupMessage> findByGroupidAndCreatetime(String groupid, Date createtime, Pageable pageable);
}
