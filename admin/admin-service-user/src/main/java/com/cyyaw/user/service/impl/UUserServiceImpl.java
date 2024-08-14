package com.cyyaw.user.service.impl;


import cn.hutool.core.util.StrUtil;
import com.cyyaw.config.exception.WebException;
import com.cyyaw.jpa.BaseDao;
import com.cyyaw.jpa.BaseService;
import com.cyyaw.user.service.UUserService;
import com.cyyaw.user.table.dao.ChFriendsUserDao;
import com.cyyaw.user.table.dao.UUserDao;
import com.cyyaw.user.table.entity.ChFriendsUser;
import com.cyyaw.user.table.entity.UUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
public class UUserServiceImpl extends BaseService<UUser, Integer> implements UUserService {

    @Autowired
    private UUserDao uUserDao;

    @Autowired
    private ChFriendsUserDao chFriendsUserDao;

    @Override
    public BaseDao getBaseDao() {
        return uUserDao;
    }

    @Override
    public UUser findByAppIdAndAccount(String appId, String account) {
        if (StrUtil.isNotBlank(appId) && StrUtil.isNotBlank(account)) {
            List<UUser> uUsers = uUserDao.findByAppIdAndAccount(appId, account);
            if (uUsers.size() == 1) {
                UUser uUser = uUsers.get(0);
                return uUser;
            } else {

            }
        } else {
            WebException.fail("您输入的信息有误");
        }
        return null;
    }


    @Override
    public UUser findByTid(String tid) {
        return uUserDao.findByTid(tid);
    }

    @Override
    public UUser findByAppIdAndPhone(String appId, String phone) {
        List<UUser> userList = uUserDao.findByAppIdAndPhone(appId, phone);
        if (userList.size() == 1) {
            return userList.get(0);
        } else {
            return null;
        }
    }


    @Override
    public List<ChFriendsUser> myFriends(String uid) {
        List<ChFriendsUser> rest = chFriendsUserDao.findAllByUserid(uid);
        List<String> userIdList = new ArrayList<>();
        for (int i = 0; i < rest.size(); i++) {
            userIdList.add(rest.get(i).getToUserId());
        }
        List<UUser> userList = uUserDao.findByTidIn(userIdList);
        for (int i = 0; i < rest.size(); i++) {
            ChFriendsUser chFriendsUser = rest.get(i);
            String toUserId = chFriendsUser.getToUserId();
            if (StrUtil.isNotBlank(toUserId)) {
                for (int j = 0; j < userList.size(); j++) {
                    UUser uUser = userList.get(j);
                    if (uUser.getTid().equals(toUserId)) {
                        chFriendsUser.setToUser(uUser);
                        break;
                    }
                }
            }
        }
        return rest;
    }

    @Override
    public void delFriends(String userId, String targetId) {
        List<ChFriendsUser> friendsUserList = chFriendsUserDao.findByUserIdAndToUserId(userId, targetId);
        for (int i = 0; i < friendsUserList.size(); i++) {
            ChFriendsUser chFriendsUser = friendsUserList.get(i);
            chFriendsUserDao.delete(chFriendsUser);
        }
    }

}

