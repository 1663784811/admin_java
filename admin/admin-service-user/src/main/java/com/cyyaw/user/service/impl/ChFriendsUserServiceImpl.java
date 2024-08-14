package com.cyyaw.user.service.impl;

import com.cyyaw.jpa.BaseDao;
import com.cyyaw.jpa.BaseService;
import com.cyyaw.user.service.ChFriendsUserService;
import com.cyyaw.user.table.dao.ChFriendsUserDao;
import com.cyyaw.user.table.entity.ChFriendsUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Slf4j
@Service
public class ChFriendsUserServiceImpl extends BaseService<ChFriendsUser, Integer> implements ChFriendsUserService {

    @Autowired
    private ChFriendsUserDao chFriendsUserDao;

    @Override
    public BaseDao getBaseDao() {
        return chFriendsUserDao;
    }


}

