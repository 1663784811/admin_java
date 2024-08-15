package com.cyyaw.user.service.impl;

import com.cyyaw.jpa.BaseDao;
import com.cyyaw.jpa.BaseService;
import com.cyyaw.user.service.ChMessageService;
import com.cyyaw.user.table.dao.ChMessageDao;
import com.cyyaw.user.table.entity.ChMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
public class ChMessageServiceImpl extends BaseService<ChMessage, Integer> implements ChMessageService {

    @Autowired
    private ChMessageDao chMessageDao;

    @Override
    public BaseDao getBaseDao() {
        return chMessageDao;
    }

    @Override
    public List<ChMessage> unReadMsgList(String userId) {
        // 查询我的私聊房间
        // 查询房间内未读消息
        return chMessageDao.findUnReadMsgByUserId(userId);
    }

    @Override
    public ChMessage readMsg(String tid) {
        ChMessage chMessage = chMessageDao.findByTid(tid);
        if (null != chMessage) {
            chMessage.setStatus(1);
            chMessageDao.save(chMessage);
        }
        return chMessage;
    }
}

