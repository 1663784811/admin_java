package com.cyyaw.user.service;


import com.cyyaw.jpa.BaseTableService;
import com.cyyaw.user.table.entity.UFriendsUser;
import com.cyyaw.user.table.entity.UUser;

import java.util.List;

public interface UUserService extends BaseTableService<UUser, Integer> {

    /**
     * 查询APP下的用户
     */
    UUser findByAppIdAndAccount(String appId, String account);

    /**
     *
     */
    UUser findByTid(String tid);


    UUser findByAppIdAndPhone(String appId, String phone);



    List<UFriendsUser> myFriends(String uid, String appId);


    void delFriends(String userId, String targetId);

}
