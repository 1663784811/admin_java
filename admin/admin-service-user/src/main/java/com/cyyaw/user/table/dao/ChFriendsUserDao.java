package com.cyyaw.user.table.dao;

import com.cyyaw.jpa.BaseDao;
import com.cyyaw.user.table.entity.ChFriendsUser;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChFriendsUserDao extends BaseDao<ChFriendsUser, Integer> {


    @Query("select m from ChFriendsUser m where m.userId = ?1 and m.toUserId = ?2")
    List<ChFriendsUser> findByUserIdAndToUserId(String userId, String targetId);


    @Query("select m from ChFriendsUser m where m.userId = ?1")
    List<ChFriendsUser> findAllByUserid(String userId);

}
