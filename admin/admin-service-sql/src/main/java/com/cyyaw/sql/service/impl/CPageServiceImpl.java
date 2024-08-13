package com.cyyaw.sql.service.impl;

import com.cyyaw.jpa.util.tools.JpaUtils;
import com.cyyaw.sql.service.CPageComponentsObjService;
import com.cyyaw.sql.table.dao.CPageComponentsObjDao;
import com.cyyaw.sql.table.entity.CPageComponentsObj;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Date;

import com.cyyaw.jpa.BaseDao;
import com.cyyaw.jpa.BaseService;
import com.cyyaw.sql.service.CPageComponentsService;
import com.cyyaw.sql.service.CPageService;
import com.cyyaw.sql.table.dao.CPageDao;
import com.cyyaw.sql.table.entity.CPage;
import com.cyyaw.sql.table.entity.CPageComponents;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class CPageServiceImpl extends BaseService<CPage, Integer> implements CPageService {

    @Autowired
    private CPageDao cPageDao;

    @Autowired
    private CPageComponentsObjDao componentsObjDao;

    @Autowired
    private CPageComponentsService cPageComponentsService;

    @Autowired
    private CPageComponentsObjService cPageComponentsObjService;

    @Override
    public BaseDao getBaseDao() {
        return cPageDao;
    }

    @Override
    public CPage findByTid(String tid) {
        CPage cPage = new CPage();
        cPage.setTid(tid);
        Example<CPage> example = Example.of(cPage);
        List<CPage> all = cPageDao.findAll(example);
        if (all.size() > 0) {
            CPage cPage1 = all.get(0);
            String tid1 = cPage1.getTid();
            CPageComponents ex = new CPageComponents();
            ex.setPageId(tid1);
            List<CPageComponents> cPageComponentsList = cPageComponentsService.findByExample(ex);
            cPage1.setComponents(cPageComponentsList);
            return cPage1;
        }
        return null;
    }

    @Override
    public CPage findByPageCode(String pageCod) {
        CPage cPage = new CPage();
        cPage.setPageCode(pageCod);
        Example<CPage> example = Example.of(cPage);
        List<CPage> all = cPageDao.findAll(example);
        if (all.size() > 0) {
            CPage cPage1 = all.get(0);
            String tid1 = cPage1.getTid();
            CPageComponents ex = new CPageComponents();
            ex.setPageId(tid1);
            List<CPageComponents> cPageComponentsList = cPageComponentsService.findByExample(ex);
            cPage1.setComponents(cPageComponentsList);
            return cPage1;
        }
        return null;
    }

    @Override
    public void copyCPage(CPage cPage) {
        CPage cp = findByPageCode(cPage.getPageCode());
        CPage target = new CPage();
        target.setName(cp.getName() + "复制");
        target.setPageIcon(cp.getPageIcon());
        target.setPageCode(cp.getPageCode() + "_copy");
        CPage save = save(target);

        List<CPageComponents> components = cp.getComponents();
        for (int i = 0; i < components.size(); i++) {
            CPageComponents co = components.get(i);
            CPageComponents obj = new CPageComponents();
            obj.setPageId(save.getTid());
            obj.setName(co.getName());
            obj.setComponentsCode(co.getComponentsCode());
            obj.setType(co.getType());
            obj.setData(co.getData());
            obj.setIcon(co.getIcon());
            obj.setSort(0);
            obj = cPageComponentsService.save(obj);
            List<CPageComponentsObj> objList = componentsObjDao.findByComponentsId(co.getTid());
            for (int j = 0; j < objList.size(); j++) {
                CPageComponentsObj cpObj = objList.get(j);
                CPageComponentsObj newCpObj = new CPageComponentsObj();
                newCpObj.setPageComponentsId(obj.getTid());
                newCpObj.setName(cpObj.getName());
                newCpObj.setDataKey(cpObj.getDataKey());
                newCpObj.setData(cpObj.getData());
                newCpObj.setSort(0);
                cPageComponentsObjService.save(newCpObj);
            }
        }
    }


}

