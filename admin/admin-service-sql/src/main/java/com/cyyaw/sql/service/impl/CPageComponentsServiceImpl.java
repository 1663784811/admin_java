package com.cyyaw.sql.service.impl;

import com.cyyaw.jpa.BaseDao;
import com.cyyaw.jpa.BaseService;
import com.cyyaw.jpa.util.tools.JpaUtils;
import com.cyyaw.sql.service.CPageComponentsService;
import com.cyyaw.sql.table.dao.CPageComponentsDao;
import com.cyyaw.sql.table.dao.CPageComponentsObjDao;
import com.cyyaw.sql.table.entity.CPageComponents;
import com.cyyaw.sql.table.entity.CPageComponentsObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CPageComponentsServiceImpl extends BaseService<CPageComponents, Integer> implements CPageComponentsService {

    @Autowired
    private CPageComponentsDao cPageComponentsDao;

    @Autowired
    private CPageComponentsObjDao componentsObjDao;

    @Override
    public BaseDao getBaseDao() {
        return cPageComponentsDao;
    }


    @Override
    public List<CPageComponents> findByPageId(String pageId) {
        List<CPageComponents> componentsList = cPageComponentsDao.findByPageId(pageId);

        List<String> tidList = JpaUtils.getTidList(componentsList);
        List<CPageComponentsObj> objList = componentsObjDao.findByPageComponentsIdIn(tidList);
        for (int i = 0; i < componentsList.size(); i++) {
            CPageComponents component = componentsList.get(i);
            List<CPageComponentsObj> setObj = new ArrayList<>();
            for (int j = 0; j < objList.size(); j++) {
                CPageComponentsObj cpObj = objList.get(j);
                if (component.getTid().equals(cpObj.getPageComponentsId())) {
                    setObj.add(cpObj);
                }
            }
            component.setObjList(setObj);
        }
        return componentsList;
    }

    @Override
    public CPageComponents findById(Integer id) {
        CPageComponents rest = cPageComponentsDao.findById(id).get();
        // 查组件Obj
//        List<CPageComponentsObj> objList = componentsObjDao.findByComponentsId(rest.getTid());
//
//
//
//        rest.getData()


        return rest;
    }

}
