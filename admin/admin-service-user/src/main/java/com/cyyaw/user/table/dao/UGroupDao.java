package com.cyyaw.user.table.dao;

import com.cyyaw.jpa.BaseDao;
import com.cyyaw.user.table.entity.UGroup;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UGroupDao extends BaseDao<UGroup, Integer> {

    @Query("select m from UGroup m where m.tid in (select t.groupId from UGroupUser t where t.userId = ?1 ) ")
    List<UGroup> findByUserid(String userid);


    UGroup findByTid(String tid);

}
