package com.cyyaw.user.table.dao;

import com.cyyaw.jpa.BaseDao;
import com.cyyaw.user.table.entity.UUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UUserDao extends BaseDao<UUser, Integer> {

    /**
     * 好友
     */
    @Query("select m from UUser m where m.tid in ( select t.toUserId from UFriendsUser t where t.userId = ?1 ) and m.appId=?2")
    List<UUser> findMyFriendsByUserid(String userid, String appId);

    @Query("select m from UUser m where m.type =?1")
    List<UUser> findByType(Integer type);

    @Query("select m from UUser m where m.tid in ( select t.userId from UGroupUser t where t.groupId = ?1)")
    List<UUser> findByGroup(String userid);

    @Query("select m from UUser m where m.account = ?1")
    List<UUser> findByAccount(String account);

    @Query("select m from UUser m where m.openId =?1 and m.unionId = ?2")
    List<UUser> findByOpenIdAndUnionid(String openid, String unionid);

    @Query("select m from UUser m where m.openId =?1")
    List<UUser> findByOpenId(String openid);

    @Query("select m from UUser m where m.tid=?1")
    UUser findByTid(String tid);

    @Query("select m from UUser m where m.appId=?1 and m.account=?2")
    List<UUser> findByAppIdAndAccount(String appId, String account);


    @Query("select m from UUser m where m.appId=?1 and m.phone=?2")
    List<UUser> findByAppIdAndPhone(String appId, String phone);

    @Query("select m from UUser m where m.tid in (:userIdList)")
    List<UUser> findByTidIn(@Param("userIdList") List<String> userIdList);
}
