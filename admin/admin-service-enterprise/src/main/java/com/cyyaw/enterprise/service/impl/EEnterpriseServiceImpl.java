package com.cyyaw.enterprise.service.impl;

import com.cyyaw.enterprise.service.EEnterpriseService;
import com.cyyaw.enterprise.table.dao.EEnterpriseDao;
import com.cyyaw.enterprise.table.entity.EEnterprise;
import com.cyyaw.jpa.BaseDao;
import com.cyyaw.jpa.BaseService;
import com.cyyaw.util.tools.WhyStringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
@Slf4j
public class EEnterpriseServiceImpl extends BaseService<EEnterprise, Integer> implements EEnterpriseService {

    @Autowired
    private EEnterpriseDao eEnterpriseDao;

    @Override
    public BaseDao getBaseDao() {
        return eEnterpriseDao;
    }

    /**
     * 注册企业
     */
    @Override
    public EEnterprise save(EEnterprise enterprise) {
        Integer id = enterprise.getId();
        if (null != id) {
            EEnterprise eEnterprise = eEnterpriseDao.findByid(id);
            BeanUtils.copyProperties(enterprise, eEnterprise);
            return eEnterpriseDao.save(eEnterprise);
        } else {
            enterprise.setCode(WhyStringUtil.getRandomString(3));
            enterprise.setUrl("http://localhost:5173/#/account/login?eCode=" + enterprise.getCode());
            enterprise.setTid(WhyStringUtil.getUUID());
            enterprise.setCreateTime(new Date());
            return eEnterpriseDao.save(enterprise);
        }
    }


    @Override
    public EEnterprise registerEnterprise(EEnterprise enterprise) {
        // 保存企业
        EEnterprise eEnterprise = save(enterprise);
        return eEnterprise;
    }

    @Override
    public EEnterprise findByCode(String code) {
        return eEnterpriseDao.findByEnterpriseByCode(code);
    }
}

