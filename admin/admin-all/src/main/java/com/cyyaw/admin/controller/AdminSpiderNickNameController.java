package com.cyyaw.admin.controller;


import com.cyyaw.spider.service.SpiderNickNameService;
import com.cyyaw.spider.table.entity.SpiderNickName;
import com.cyyaw.util.tools.BaseResult;
import com.cyyaw.util.tools.WhyStringUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RestController
@Api(tags = "抓取标签")
@RequestMapping("/admin/{eCode}/spider/nickname")
public class AdminSpiderNickNameController {

    @Autowired
    private SpiderNickNameService spiderNickNameService;

    /**
     * 添加或修改
     */
    @PostMapping("/saveSpiderNickName")
    public BaseResult saveSpiderNickName(@RequestBody SpiderNickName saveObj) {
        SpiderNickName obj = null;
        Integer id = saveObj.getId();
        if (ObjectUtils.isEmpty(id)) {
            //添加
            saveObj.setCreateTime(new Date());
            saveObj.setTid(WhyStringUtil.getUUID());
            log.info("添加:{}", saveObj);
            obj = spiderNickNameService.save(saveObj);
        } else {
            //修改
            log.info("修改:{}", saveObj);
            SpiderNickName cpObj = spiderNickNameService.findId(id);
            Assert.notNull(cpObj, "操作失败！");
            BeanUtils.copyProperties(saveObj,cpObj);
            obj = spiderNickNameService.save(cpObj);
        }
        return BaseResult.ok(obj);
    }


}
