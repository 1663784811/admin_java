package com.cyyaw.user.service.impl;

import com.cyyaw.jpa.BaseDao;
import com.cyyaw.jpa.BaseService;
import com.cyyaw.user.service.TAdminService;
import com.cyyaw.user.table.dao.TAdminDao;
import com.cyyaw.user.table.entity.TAdmin;
import com.cyyaw.util.tools.WhyStringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class TAdminServiceImpl extends BaseService<TAdmin, Integer> implements TAdminService {

    @Autowired
    private TAdminDao tAdminDao;

    @Override
    public BaseDao getBaseDao() {
        return tAdminDao;
    }


    @Override
    public TAdmin findByAccount(String enterpriseCode, String account) {
        return tAdminDao.findByAccount(enterpriseCode, account);
    }

    @Override
    public TAdmin save(TAdmin admin) {
        Integer id = admin.getId();
        if (null != id) {
            TAdmin tAdmin = tAdminDao.findByid(id);
            BeanUtils.copyProperties(admin, tAdmin);
            return tAdminDao.save(tAdmin);
        } else {
            admin.setTid(WhyStringUtil.getUUID());
            admin.setCreateTime(new Date());
            admin.setDel(0);
            return tAdminDao.save(admin);
        }
    }
}
