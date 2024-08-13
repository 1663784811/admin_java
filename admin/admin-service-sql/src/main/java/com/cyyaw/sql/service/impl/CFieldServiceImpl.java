package com.cyyaw.sql.service.impl;

import com.cyyaw.sql.service.CFieldService;
import com.cyyaw.sql.table.dao.CFieldDao;
import com.cyyaw.sql.table.entity.CField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CFieldServiceImpl implements CFieldService {

    @Autowired
    private CFieldDao cFieldDao;


    @Override
    public List<CField> findByCPageComponentsId(String cPageComponentsTid) {
        return cFieldDao.findByCPageComponentsId(cPageComponentsTid);
    }
}
