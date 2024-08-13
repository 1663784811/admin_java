package com.cyyaw.user.service.impl;

import com.cyyaw.jpa.BaseDao;
import com.cyyaw.jpa.BaseService;
import com.cyyaw.user.service.TDepartmentService;
import com.cyyaw.user.table.dao.TDepartmentDao;
import com.cyyaw.user.table.entity.TDepartment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class TDepartmentServiceImpl extends BaseService<TDepartment, Integer> implements TDepartmentService {

    @Autowired
    private TDepartmentDao tDepartmentDao;

    @Override
    public BaseDao getBaseDao() {
        return tDepartmentDao;
    }

}

