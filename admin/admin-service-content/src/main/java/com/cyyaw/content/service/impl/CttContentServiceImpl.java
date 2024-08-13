package com.cyyaw.content.service.impl;

import com.cyyaw.content.service.CttContentService;
import com.cyyaw.content.table.dao.CttContentDao;
import com.cyyaw.content.table.entity.CttContent;
import com.cyyaw.jpa.BaseDao;
import com.cyyaw.jpa.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Slf4j
@Service
public class CttContentServiceImpl extends BaseService<CttContent, Integer> implements CttContentService {

    @Autowired
    private CttContentDao cttContentDao;

    @Override
    public BaseDao getBaseDao() {
        return cttContentDao;
    }

}

