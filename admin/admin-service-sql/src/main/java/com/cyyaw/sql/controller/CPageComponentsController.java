package com.cyyaw.sql.controller;

import cn.hutool.json.JSONObject;
import com.cyyaw.sql.service.CPageComponentsService;
import com.cyyaw.sql.table.entity.CPageComponents;
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
@RequestMapping("/admin/config/cpagecomponents")
@RestController
public class CPageComponentsController {

    @Autowired
    private CPageComponentsService cPageComponentsService;

    /**
     * 分页条件查询
     */
    @GetMapping("/findPage")
    public BaseResult<CPageComponents> findPageCPageComponents(@RequestParam Map<String, Object> map) {
        PageRespone<CPageComponents> page = cPageComponentsService.findPage(new JSONObject(map));
        return BaseResult.ok(page);
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/findIdCPageComponents")
    public BaseResult findIdCPageComponents(Integer id) {
        CPageComponents obj = cPageComponentsService.findId(id);
        return BaseResult.ok(obj);
    }


    /**
     * 添加或修改
     */
    @PostMapping("/saveCPageComponents")
    public BaseResult saveCPageComponents(@RequestBody CPageComponents saveObj) {
        CPageComponents obj = null;
        Integer id = saveObj.getId();
        if (ObjectUtils.isEmpty(id)) {
            //添加
            saveObj.setCreateTime(new Date());
            saveObj.setTid(WhyStringUtil.getUUID());
            log.info("添加:{}", saveObj);
            obj = cPageComponentsService.save(saveObj);
        } else {
            //修改
            log.info("修改:{}", saveObj);
            CPageComponents cpObj = cPageComponentsService.findId(id);
            Assert.notNull(cpObj, "操作失败！");
            BeanUtils.copyProperties(saveObj,cpObj);
            obj = cPageComponentsService.save(cpObj);
        }
        return BaseResult.ok(obj);
    }
}
