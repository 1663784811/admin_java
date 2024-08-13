package com.cyyaw.content.service.impl;

import com.cyyaw.content.service.CttCommentService;
import com.cyyaw.content.table.dao.CttCommentDao;
import com.cyyaw.content.table.entity.CttComment;
import com.cyyaw.jpa.BaseDao;
import com.cyyaw.jpa.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Slf4j
@Service
public class CttCommentServiceImpl extends BaseService<CttComment, Integer> implements CttCommentService {

    @Autowired
    private CttCommentDao cttCommentDao;

    @Override
    public BaseDao getBaseDao() {
        return cttCommentDao;
    }

}

