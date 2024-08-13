package com.cyyaw.user.service.impl;

import cn.hutool.core.util.StrUtil;
import com.cyyaw.jpa.BaseDao;
import com.cyyaw.jpa.BaseService;
import com.cyyaw.user.service.TPowerService;
import com.cyyaw.user.table.dao.TPowerDao;
import com.cyyaw.user.table.entity.TPower;
import com.cyyaw.user.utils.PowerCode;
import com.cyyaw.user.utils.entity.TreeEntity;
import com.cyyaw.util.tools.BaseResult;
import com.cyyaw.util.tools.WhyStringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@Slf4j
public class TPowerServiceImpl extends BaseService<TPower, Integer> implements TPowerService {

    @Autowired
    private TPowerDao tPowerDao;

    @Override
    public BaseDao getBaseDao() {
        return tPowerDao;
    }

    @Override
    public List<TPower> findAdminPower(String tid) {
        return tPowerDao.findAdminPower(tid);
    }

    @Override
    public List<TPower> initPower(String enterpriseCode) {
        PowerCode[] powerCodes = PowerCode.values();
        List<TPower> arr = new ArrayList<>();
        for (int i = 0; i < powerCodes.length; i++) {
            PowerCode powerCode = powerCodes[i];
            String code = powerCode.getCode();

            TPower tPower = new TPower();
            tPower.setTid(WhyStringUtil.getUUID());
            tPower.setPid("");
            tPower.setTreeCode("");
            tPower.setCode("");
            tPower.setIcon("");
            tPower.setName("");
            tPower.setPowerType(0);
            tPower.setStatus(0);
            tPower.setIsPower(0);
            tPower.setUrl("");
            tPower.setSort(0);
            tPower.setEnterpriseCode(enterpriseCode);
            TPower save = tPowerDao.save(tPower);
            arr.add(save);
        }
        return arr;
    }

    @Override
    public BaseResult<TreeEntity<TPower>> queryMenu(String eCode, Integer powerType) {
        List<TPower> powers = tPowerDao.findAllByEnterpriseCodeAndType(eCode, powerType);
        // 组装成树
        TreeEntity<TPower> treeEntity = new TreeEntity<>();
        for (TPower tpower : powers) {
            TreeEntity.Node<TPower> node = new TreeEntity.Node<TPower>();
            node.setTid(tpower.getTid());
            node.setData(tpower);
            node.setTitle(tpower.getName());
            node.setPid(tpower.getPid());
            treeEntity.add(node);
        }
        return BaseResult.ok(treeEntity);
    }

    @Override
    public BaseResult delMenu(Integer id) {
        // 第一步：查询菜单
        TPower tPower = tPowerDao.findByid(id);
        if (null != tPower) {
            // 删除子菜单
            delChildrenMenu(tPower.getTid());
            // 删除要删除的菜单
            tPowerDao.delete(tPower);
        }
        return BaseResult.fail("没有找到指定的菜单");
    }

    public void delChildrenMenu(String pid) {
        if (StrUtil.isNotBlank(pid)) {
            TPower tp = new TPower();
            tp.setPid(pid);
            Example<TPower> example = Example.of(tp);
            List<TPower> tPowerList = tPowerDao.findAll(example);
            if (null != tPowerList && tPowerList.size() > 0) {
                for (int i = 0; i < tPowerList.size(); i++) {
                    TPower tPower = tPowerList.get(i);
                    String tid = tPower.getTid();
                    delChildrenMenu(tid);
                    tPowerDao.delete(tPower);
                }
            }
        }
    }


    @Override
    public TPower save(TPower tPower, String eCode) {
        Integer id = tPower.getId();
        if (null != id) {
            // 修改
            TPower obj = tPowerDao.findByid(id);
            BeanUtils.copyProperties(tPower, obj);
            // 查询父节点
            String pid = obj.getPid();
            TPower parent = tPowerDao.findByTid(pid);
            // 查询子节点
            return tPowerDao.save(obj);
        } else {
            // 添加
            tPower.setTid(WhyStringUtil.getUUID());
            tPower.setCreateTime(new Date());
            tPower.setDel(0);
            if (StrUtil.isBlank(tPower.getEnterpriseCode())) {
                tPower.setEnterpriseCode(eCode);
            }
            if (tPower.getIsPower() == null) {
                tPower.setIsPower(0);
            }
            if (tPower.getTreeCode() == null) {
                tPower.setTreeCode("");
            }
            return tPowerDao.save(tPower);
        }
    }

}

