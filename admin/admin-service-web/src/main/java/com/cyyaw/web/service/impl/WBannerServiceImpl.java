package com.cyyaw.web.service.impl;

import com.cyyaw.jpa.BaseDao;
import com.cyyaw.jpa.BaseService;
import com.cyyaw.web.service.WBannerService;
import com.cyyaw.web.service.WebImageService;
import com.cyyaw.web.table.dao.WBannerDao;
import com.cyyaw.web.table.dao.WebImageDao;
import com.cyyaw.web.table.entity.WBanner;
import com.cyyaw.web.table.entity.WebImage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class WBannerServiceImpl extends BaseService<WBanner, Integer> implements WBannerService {

    @Autowired
    private WBannerDao wBannerDao;

    @Override
    public BaseDao getBaseDao() {
        return wBannerDao;
    }

}

