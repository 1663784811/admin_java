package com.cyyaw.store.service.impl;

import com.cyyaw.jpa.BaseDao;
import com.cyyaw.jpa.BaseService;
import com.cyyaw.store.service.GPhotoService;
import com.cyyaw.store.table.goods.dao.GPhotoDao;
import com.cyyaw.store.table.goods.entity.GPhoto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Slf4j
@Service
public class GPhotoServiceImpl extends BaseService<GPhoto, Integer> implements GPhotoService {

    @Autowired
    private GPhotoDao gPhotoDao;

    @Override
    public BaseDao getBaseDao() {
        return gPhotoDao;
    }

}

