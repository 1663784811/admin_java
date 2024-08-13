package com.cyyaw.user.table.dao;

import com.cyyaw.jpa.BaseDao;
import com.cyyaw.user.table.entity.UFriendsUser;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UFriendsUserDao extends BaseDao<UFriendsUser, Integer> {


    @Query("select m from UFriendsUser m where m.userId = ?1")
    List<UFriendsUser> findAllByUserid(String uid);



    @Query("select m from UFriendsUser m where m.userId = ?1 and m.toUserId = ?2")
    List<UFriendsUser> findByUserIdAndToUserId(String userId, String targetId);



}
