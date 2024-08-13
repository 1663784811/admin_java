package com.cyyaw.enterprise.controller;

import cn.hutool.json.JSONObject;
import com.cyyaw.enterprise.service.EProductCenterService;
import com.cyyaw.enterprise.table.entity.EProductCenter;
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
import java.util.Map;

@Slf4j
@RequestMapping("/admin/eProductCenter")
@RestController
public class EProductCenterController {

    @Autowired
    private EProductCenterService eProductCenterService;

    /**
     * 分页条件查询
     */
    @GetMapping("/findPage")
    public BaseResult<EProductCenter> findPageEProductCenter(@RequestParam Map<String, Object> map) {
        PageRespone<EProductCenter> page = eProductCenterService.findPage(new JSONObject(map));
        return BaseResult.ok(page);
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/findIdEProductCenter")
    public BaseResult findIdEProductCenter(Integer id) {
        EProductCenter obj = eProductCenterService.findId(id);
        return BaseResult.ok(obj);
    }


    /**
     * 添加或修改
     */
    @PostMapping("/saveEProductCenter")
    public BaseResult saveEProductCenter(@RequestBody EProductCenter saveObj) {
        EProductCenter obj = null;
        Integer id = saveObj.getId();
        if (ObjectUtils.isEmpty(id)) {
            //添加
            saveObj.setCreateTime(new Date());
            saveObj.setTid(WhyStringUtil.getUUID());
            log.info("添加:{}", saveObj);
            obj = eProductCenterService.save(saveObj);
        } else {
            //修改
            log.info("修改:{}", saveObj);
            EProductCenter cpObj = eProductCenterService.findId(id);
            Assert.notNull(cpObj, "操作失败！");
            BeanUtils.copyProperties(saveObj, cpObj);
            obj = eProductCenterService.save(cpObj);
        }
        return BaseResult.ok(obj);
    }

}
