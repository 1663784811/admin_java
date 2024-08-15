package com.cyyaw.user.service;

import com.cyyaw.jpa.BaseTableService;
import com.cyyaw.user.table.entity.ChMessage;

import java.util.List;

public interface ChMessageService extends BaseTableService<ChMessage, Integer> {


    List<ChMessage> unReadMsgList(String userId);


    ChMessage readMsg(String tid);

}
