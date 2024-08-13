package com.cyyaw.service.impl;

import com.cyyaw.service.AdminUserService;
import com.cyyaw.user.table.dao.TPowerDao;
import com.cyyaw.user.table.dao.TRoleDao;
import com.cyyaw.user.table.entity.TPower;
import com.cyyaw.user.table.entity.TRole;
import com.cyyaw.user.utils.entity.TreeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class AdminUserServiceImpl implements AdminUserService {


    @Autowired
    private TRoleDao tRoleDao;

    @Autowired
    private TPowerDao tPowerDao;


    @Override
    public List<TreeEntity.Node<TPower>> adminMenu(String adminId, Integer userType) {
        // 第一步: 查询用户角色
        List<TRole> roles = tRoleDao.findByAdminId(adminId);
        // 第二步: 查询角色权限
        List<String> rolesId = roles.stream().map(TRole::getTid).collect(Collectors.toList());
        List<TPower> powers = tPowerDao.findPowerByRoleAndUserType(rolesId, userType);
        // 第三步: 整理数据
        TreeEntity treeEntity = new TreeEntity();
        for (TPower tpower : powers) {
            TreeEntity.Node<TPower> node = new TreeEntity.Node<TPower>();
            node.setTid(tpower.getTid());
            node.setData(tpower);
            node.setTitle(tpower.getName());
            node.setPid(tpower.getPid());
            treeEntity.add(node);
        }
        return treeEntity.getRoot();
    }


}
