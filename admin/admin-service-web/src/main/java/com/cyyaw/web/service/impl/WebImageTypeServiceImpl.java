package com.cyyaw.web.service.impl;

import com.cyyaw.jpa.BaseDao;
import com.cyyaw.jpa.BaseService;
import com.cyyaw.web.service.WebImageTypeService;
import com.cyyaw.web.table.dao.WebImageTypeDao;
import com.cyyaw.web.table.entity.WebImageType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class WebImageTypeServiceImpl extends BaseService<WebImageType, Integer> implements WebImageTypeService {

    @Autowired
    private WebImageTypeDao webImageTypeDao;

    @Override
    public BaseDao getBaseDao() {
        return webImageTypeDao;
    }

}

