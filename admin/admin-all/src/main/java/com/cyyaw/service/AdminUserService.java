package com.cyyaw.service;

import com.cyyaw.user.table.entity.TPower;
import com.cyyaw.user.utils.entity.TreeEntity;

import java.util.List;

public interface AdminUserService {

    /**
     * 获取用户菜单
     *
     * @return
     */
    List<TreeEntity.Node<TPower>> adminMenu(String adminId, Integer userType);


}
