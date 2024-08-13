package com.cyyaw.user.service;


import com.cyyaw.jpa.BaseTableService;
import com.cyyaw.user.table.entity.TPower;
import com.cyyaw.user.utils.entity.TreeEntity;
import com.cyyaw.util.tools.BaseResult;

import java.util.List;

public interface TPowerService extends BaseTableService<TPower, Integer> {


    TPower save(TPower tPower, String eCode);

    /**
     * 查询用户权限
     */
    List<TPower> findAdminPower(String tid);

    /**
     * 初始化菜单
     */
    List<TPower> initPower(String enterpriseCode);


    /**
     * 查询菜单
     */
    BaseResult<TreeEntity<TPower>> queryMenu(String eCode, Integer powerType);

    /**
     * 删除菜单
     */
    BaseResult delMenu(Integer id);


}
