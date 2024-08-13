package com.cyyaw.service.impl;

import com.cyyaw.service.RoleService;
import com.cyyaw.user.service.TDepartmentService;
import com.cyyaw.user.table.entity.TDepartment;
import com.cyyaw.user.utils.entity.TreeEntity;
import com.cyyaw.util.tools.BaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private TDepartmentService tDepartmentService;


    @Override
    public BaseResult findDepartment(TDepartment tDepartment) {
        List<TDepartment> tDepartmentList = tDepartmentService.findByExample(tDepartment);
        // 组装成树
        TreeEntity treeEntity = new TreeEntity();
        for (TDepartment department : tDepartmentList) {
            TreeEntity.Node<TDepartment> node = new TreeEntity.Node<TDepartment>();
            node.setTid(department.getTid());
            node.setData(department);
            node.setTitle(department.getName());
            node.setPid(department.getPid());
            treeEntity.add(node);
        }
        return BaseResult.ok(treeEntity);
    }
}
