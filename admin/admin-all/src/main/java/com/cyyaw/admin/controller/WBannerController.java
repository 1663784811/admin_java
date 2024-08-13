package com.cyyaw.admin.controller;

import cn.hutool.json.JSONObject;
import com.cyyaw.util.tools.BaseResult;
import com.cyyaw.util.tools.PageRespone;
import com.cyyaw.util.tools.WhyStringUtil;
import com.cyyaw.web.service.WBannerService;
import com.cyyaw.web.table.entity.WBanner;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@Slf4j
@RestController
@Api(tags = "Banner图")
@RequestMapping("/admin/{eCode}/banner")
public class WBannerController {

    @Autowired
    private WBannerService wBannerService;

    /**
     * 添加或修改
     */
    @PostMapping("/saveWBanner")
    public BaseResult saveWBanner(@RequestBody WBanner saveObj) {
        WBanner obj = null;
        Integer id = saveObj.getId();
        if (ObjectUtils.isEmpty(id)) {
            //添加
            saveObj.setCreateTime(new Date());
            saveObj.setTid(WhyStringUtil.getUUID());
            log.info("添加:{}", saveObj);
            obj = wBannerService.save(saveObj);
        } else {
            //修改
            log.info("修改:{}", saveObj);
            WBanner cpObj = wBannerService.findId(id);
            Assert.notNull(cpObj, "操作失败！");
            BeanUtils.copyProperties(saveObj, cpObj);
            obj = wBannerService.save(cpObj);
        }
        return BaseResult.ok(obj);
    }

}
