package com.cyyaw.user.controller;

import cn.hutool.json.JSONObject;
import com.cyyaw.user.service.TPowerService;
import com.cyyaw.user.table.entity.TPower;
import com.cyyaw.user.utils.entity.TreeEntity;
import com.cyyaw.util.tools.BaseResult;
import com.cyyaw.util.tools.PageRespone;
import com.cyyaw.util.tools.WhyStringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("/admin/tPower")
@RestController
public class TPowerController {

    @Autowired
    private TPowerService tPowerService;

    /**
     * 分页条件查询
     */
    @GetMapping("/findPage")
    public BaseResult<TPower> findPageTPower(@RequestParam Map<String, Object> map) {
        PageRespone<TPower> page = tPowerService.findPage(new JSONObject(map));
        List<TPower> powers = page.getContent();
        TreeEntity treeEntity = new TreeEntity();
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

    /**
     * 根据ID查询
     */
    @GetMapping("/findIdTPower")
    public BaseResult findIdTPower(Integer id) {
        TPower obj = tPowerService.findId(id);
        return BaseResult.ok(obj);
    }


    /**
     * 添加或修改
     */
    @PostMapping("/saveTPower")
    public BaseResult saveTPower(@RequestBody TPower saveObj) {
        TPower obj = null;
        Integer id = saveObj.getId();
        if (ObjectUtils.isEmpty(id)) {
            //添加
            saveObj.setCreateTime(new Date());
            saveObj.setTid(WhyStringUtil.getUUID());
            log.info("添加:{}", saveObj);
            obj = tPowerService.save(saveObj);
        } else {
            //修改
            log.info("修改:{}", saveObj);
            TPower cpObj = tPowerService.findId(id);
            Assert.notNull(cpObj, "操作失败！");
            BeanUtils.copyProperties(saveObj,cpObj);
            obj = tPowerService.save(cpObj);
        }
        return BaseResult.ok(obj);
    }

    /**
     * 删除
     */
    @PostMapping("/delTPower")
    public BaseResult delTPower(@RequestBody Integer idArr[]) {
        tPowerService.del(idArr);
        return BaseResult.ok("删除成功");
    }

}
