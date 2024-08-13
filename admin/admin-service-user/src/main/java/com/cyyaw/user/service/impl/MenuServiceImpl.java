package com.cyyaw.user.service.impl;

import com.cyyaw.user.service.MenuService;
import com.cyyaw.user.table.dao.TPowerDao;
import com.cyyaw.user.table.entity.TPower;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {


    @Autowired
    private TPowerDao tPowerDao;


    @Override
    public List<TPower> getAdminMenu(String tid) {
        return tPowerDao.getAdminMenu(tid);
    }

}
