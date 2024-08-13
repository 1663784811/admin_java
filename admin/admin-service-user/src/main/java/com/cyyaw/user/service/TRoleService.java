package com.cyyaw.user.service;


import com.cyyaw.jpa.BaseTableService;
import com.cyyaw.user.table.entity.TRole;

public interface TRoleService extends BaseTableService<TRole, Integer> {

    /**
     * 初始化权限
     */
    void initRole(String enterpriseCode, String adminId);


}
