package com.cyyaw.user.service.impl;

import com.cyyaw.jpa.BaseDao;
import com.cyyaw.jpa.BaseService;
import com.cyyaw.user.service.TPowerService;
import com.cyyaw.user.service.TRoleService;
import com.cyyaw.user.table.dao.TAdminRoleDao;
import com.cyyaw.user.table.dao.TRoleDao;
import com.cyyaw.user.table.dao.TRolePowerDao;
import com.cyyaw.user.table.entity.TAdminRole;
import com.cyyaw.user.table.entity.TPower;
import com.cyyaw.user.table.entity.TRole;
import com.cyyaw.user.table.entity.TRolePower;
import com.cyyaw.user.utils.RoleCode;
import com.cyyaw.util.tools.WhyStringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class TRoleServiceImpl extends BaseService<TRole, Integer> implements TRoleService {

    @Autowired
    private TPowerService tPowerService;

    @Autowired
    private TRoleDao tRoleDao;

    @Autowired
    private TAdminRoleDao tAdminRoleDao;

    @Autowired
    private TRolePowerDao tRolePowerDao;

    @Override
    public BaseDao getBaseDao() {
        return tRoleDao;
    }


    @Override
    public void initRole(String enterpriseCode, String adminId) {
        // 初始化菜单
        List<TPower> tPowers = tPowerService.initPower(enterpriseCode);
        // 初始化角色
        TRole tRole = new TRole();
        tRole.setCode(RoleCode.admin.getCode());
        tRole.setName("负责人");
        tRole.setTreeCode("");
        tRole.setEnterpriseCode(enterpriseCode);
        TRole save = this.save(tRole);
        String roleId = save.getTid();
        // 绑定菜单
        binPower(tPowers, roleId);
        // 绑定角色
        binRole(adminId, roleId);
    }


    /**
     * 绑定菜单
     */
    public void binPower(List<TPower> tPowers, String roleId) {
        log.info("------------绑定菜单 ----{}----{} ", tPowers, roleId);
        for (int i = 0; i < tPowers.size(); i++) {
            TPower tPower = tPowers.get(i);
            TRolePower tRolePower = new TRolePower();
            tRolePower.setTid(WhyStringUtil.getUUID());
            tRolePower.setCreateTime(new Date());
            tRolePower.setDel(0);
            tRolePower.setNote("初始化绑定");
            tRolePower.setPowerId(tPower.getTid());
            tRolePower.setRoleId(roleId);
            tRolePowerDao.save(tRolePower);
        }
    }

    /**
     * 绑定角色
     */
    public TAdminRole binRole(String adminId, String roleId) {
        log.info("------------绑定角色 ----{}----{} ", adminId, roleId);
        List<TAdminRole> roles = tAdminRoleDao.findByAdminIdAndRoleID(adminId, roleId);
        if (roles.size() > 0) {
            return roles.get(0);
        } else {
            TAdminRole obj = new TAdminRole();
            obj.setTid(WhyStringUtil.getUUID());
            obj.setCreateTime(new Date());
            obj.setDel(0);
            obj.setNote("初始化");
            obj.setAdminId(adminId);
            obj.setRoleId(roleId);
            return tAdminRoleDao.save(obj);
        }
    }


}
